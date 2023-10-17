package it.mgg.siapafismw.service;

import it.mgg.siapafismw.dto.JwtAuthenticationResponseDTO;
import it.mgg.siapafismw.dto.SignUpRequestDTO;
import it.mgg.siapafismw.dto.SigninRequestDTO;

public interface AuthenticationService 
{
	JwtAuthenticationResponseDTO signup(SignUpRequestDTO request);
    JwtAuthenticationResponseDTO signin(SigninRequestDTO request);
}
