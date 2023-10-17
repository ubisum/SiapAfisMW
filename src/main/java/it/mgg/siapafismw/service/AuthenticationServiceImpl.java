package it.mgg.siapafismw.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dto.JwtAuthenticationResponseDTO;
import it.mgg.siapafismw.dto.SignUpRequestDTO;
import it.mgg.siapafismw.dto.SigninRequestDTO;
import it.mgg.siapafismw.dto.UtenteDTO;
import it.mgg.siapafismw.enums.Role;
import it.mgg.siapafismw.model.Utente;
import it.mgg.siapafismw.repositories.UtenteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService 
{
	@Autowired
	private UtenteRepository userRepository;
	
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    @Override
    public JwtAuthenticationResponseDTO signup(SignUpRequestDTO request) {
        UtenteDTO utenteDTO = UtenteDTO.builder().nome(request.getNome()).cognome(request.getCognome()).username(request.getUsername())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        
        ModelMapper mapper = new ModelMapper();
        
        Utente utenteSalvato = userRepository.save(mapper.map(utenteDTO,  Utente.class));
        var jwt = jwtService.generateToken(mapper.map(utenteSalvato, UtenteDTO.class));
        return JwtAuthenticationResponseDTO.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponseDTO signin(SigninRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        Utente user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username o password non validi"));
        
        ModelMapper mapper = new ModelMapper();
        var jwt = jwtService.generateToken(mapper.map(user, UtenteDTO.class));
        
        return JwtAuthenticationResponseDTO.builder().token(jwt).build();
    }

}
