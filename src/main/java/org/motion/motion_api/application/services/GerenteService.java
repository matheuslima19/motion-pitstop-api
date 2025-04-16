package org.motion.motion_api.application.services;


import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.motion.motion_api.application.exceptions.DadoUnicoDuplicadoException;
import org.motion.motion_api.application.exceptions.InvalidCredentialsException;
import org.motion.motion_api.application.exceptions.RecursoNaoEncontradoException;
import org.motion.motion_api.application.services.authorization.AuthorizationService;
import org.motion.motion_api.application.services.observer.AccountCreationNotificationObserver;
import org.motion.motion_api.application.services.observer.AccountCreationNotificationObserver.AccountCreationData;
import org.motion.motion_api.application.services.observer.Subject;
import org.motion.motion_api.application.services.strategies.GerenteServiceStrategy;
import org.motion.motion_api.application.services.util.ServiceHelper;
import org.motion.motion_api.domain.dtos.pitstop.gerente.*;
import org.motion.motion_api.domain.entities.Oficina;
import org.motion.motion_api.domain.entities.pitstop.Gerente;
import org.motion.motion_api.domain.repositories.pitstop.IGerenteRepository;
import org.motion.motion_api.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;


@Service
public class GerenteService implements GerenteServiceStrategy {

    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    private IGerenteRepository gerenteRepository;
    @Autowired
    private ServiceHelper serviceHelper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private JavaMailSender emailSender;
    private Subject subject = new Subject();

    @PostConstruct
    public void init() {
        subject.addObserver(new AccountCreationNotificationObserver());
    }

    public List<Gerente> listarTodos() {
        return gerenteRepository.findAll();
    }

    public Gerente buscarPorId(int id) {
        return serviceHelper.pegarGerenteValido(id);
    }

    public Gerente criar(CreateGerenteDTO novoGerenteDTO) throws MessagingException {
        Oficina oficina = serviceHelper.pegarOficinaValida(novoGerenteDTO.fkOficina());
        verificarEmailDuplicado(novoGerenteDTO.email());

        //oficinaComGerenteCadastrado(oficina);
        String senhaGerada = geradorDeSenhaAleatoria();
        String senhaCriptografada = new BCryptPasswordEncoder().encode(senhaGerada);

        Gerente gerente = new Gerente(novoGerenteDTO, oficina, senhaCriptografada);

        //subject.notifyObservers(new AccountCreationData(gerente, senhaGerada, emailSender));
        gerenteRepository.save(gerente);

        return gerente;
    }


    @Transactional
    public Gerente atualizar(int id, UpdateGerenteDTO updateGerenteDTO) {
        Gerente gerente = buscarPorId(id);

        gerente.setNome(updateGerenteDTO.nome());
        gerente.setSobrenome(updateGerenteDTO.sobrenome());

        gerenteRepository.save(gerente);
        return gerente;
    }

    public void deletar(int id) {
        Gerente gerente = buscarPorId(id);
        gerenteRepository.delete(gerente);
    }

    @Transactional
    public Gerente atualizarSenha(int id, UpdateSenhaGerenteDTO updateSenhaGerenteDTO) {
        Gerente gerente = buscarPorId(id);
        String senhaCriptografada = new BCryptPasswordEncoder().encode(updateSenhaGerenteDTO.senha());
        gerente.setSenha(senhaCriptografada);
        gerenteRepository.save(gerente);
        return gerente;
    }


    public LoginGerenteResponse login(@Valid LoginGerenteRequest request) {
        Gerente gerente = gerenteRepository.findGerenteByEmail(request.email());
        if (gerente == null)
            throw new RecursoNaoEncontradoException("Usuário não encontrado com email: " + request.email());

        var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
        var auth = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((Gerente) auth.getPrincipal());

        return new LoginGerenteResponse(gerente.getId(), gerente.getEmail(), gerente.getNome(), gerente.getSobrenome(),gerente.getFotoUrl(), gerente.getOficina(), token);
    }

    @Transactional
    public Gerente atualizarUrlFoto(int id, UpdateFotoGerenteDTO dto) {
        Gerente gerente = buscarPorId(id);
        gerente.setFotoUrl(dto.getUrl());
        gerenteRepository.save(gerente);
        return gerente;
    }

    public void enviarTokenConfirmacao(String email, String op) throws MessagingException {
        Gerente gerente = gerenteRepository.findGerenteByEmail(email);
        if (gerente == null) throw new RecursoNaoEncontradoException("Email não encontrado no sistema");

        String token = geradorDeSenhaAleatoria();
        gerente.setConfirmToken(token);
        gerenteRepository.save(gerente);

        if (op.equalsIgnoreCase("senha"))
            emailRecuperacao(gerente);
        else if (op.equalsIgnoreCase("email")) {
            emailTokenConfirmacao(email, token);
        }
    }

    public void validarTokenConfirmacao(ConfirmTokenDTO dto, String op) {
        Gerente gerente = gerenteRepository.findGerenteByEmail(dto.getEmail());
        if (gerente == null) throw new RecursoNaoEncontradoException("Email não encontrado no sistema");
        if (gerente.getConfirmToken() == null) throw new RecursoNaoEncontradoException("Token não foi gerado");
        if (!gerente.getConfirmToken().equalsIgnoreCase(dto.getToken()))
            throw new InvalidCredentialsException("Token inválido");

        gerente.setConfirmToken(null);
        if(op.equalsIgnoreCase("senha")){
            String senhaCriptografada = new BCryptPasswordEncoder().encode(dto.getSenha());
            gerente.setSenha(senhaCriptografada);
        }
        gerenteRepository.save(gerente);
    }

    private void verificarEmailDuplicado(String email) {
        if (gerenteRepository.existsByEmail(email)) {
            throw new DadoUnicoDuplicadoException("Email já cadastrado");
        }
    }


    private void oficinaComGerenteCadastrado(Oficina oficina) {
        if (gerenteRepository.existsByOficina(oficina))
            throw new DadoUnicoDuplicadoException("Oficina com gerente já cadastrado");
    }


    private String geradorDeSenhaAleatoria() {
        final String CARACTERES_PERMITIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int indice = random.nextInt(CARACTERES_PERMITIDOS.length());
            sb.append(CARACTERES_PERMITIDOS.charAt(indice));
        }
        return sb.toString();
    }


    private void emailRecuperacao(Gerente gerente) throws MessagingException {

        String htmlTemplate = "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <link href='https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap' rel='stylesheet'>" +
                "</head>" +
                "<body style=\"font-family: Roboto,sans-serif;\">" +
                "    <h2>Olá, %s.</h2><br>" +
                "    <span>Insira este código para concluir a redefinição</span><br>" +
                "    <h1>%s</h1>" +
                "    <span>Se você não solicitou esse código, recomendamos que altere sua senha.</span>" +
                "</body>" +
                "</html>";

        String htmlContent = String.format(htmlTemplate, gerente.getNome(), gerente.getConfirmToken());


        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(gerente.getEmail());
        helper.setSubject("Recuperação de senha");
        helper.setText(htmlContent, true);
        emailSender.send(message);
    }


    private void emailTokenConfirmacao(String email, String token) throws MessagingException {
        String htmlBody = "<html>" +
                "<head><link href='https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap' rel='stylesheet'></head>" +
                "<body style='font-family: Roboto, sans-serif;'><h2>Confirme seu email utilizando o token abaixo.<br></h2>" +
                "<p>Token de confirmação: <strong>" + token + "</strong></p>" +
                "<p>Atenciosamente,</p>" +
                "<p>A equipe motion</p>" +
                "</body></html>";


        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(email);
        helper.setSubject("Confirme seu email motion");
        helper.setText(htmlBody, true); // Habilita o processamento de HTML
        emailSender.send(message);
    }


}
