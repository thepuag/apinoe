package com.noe.apinoe.config;

import com.noe.apinoe.model.Usuario;
import com.noe.apinoe.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        // Extraer información del usuario de Google
        String email = oauth2User.getAttribute("email");
        String nombre = oauth2User.getAttribute("name");
        String googleId = oauth2User.getAttribute("sub");
        String imagenUrl = oauth2User.getAttribute("picture");
        
        // Buscar o crear usuario en la base de datos
        Optional<Usuario> usuarioExistente = usuarioService.findByEmail(email);
        
        if (usuarioExistente.isEmpty()) {
            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setGoogleId(googleId);
            nuevoUsuario.setImagenUrl(imagenUrl);
            nuevoUsuario.setActivo(true);
            
            usuarioService.save(nuevoUsuario);
        } else {
            // Actualizar información del usuario existente si es necesario
            Usuario usuario = usuarioExistente.get();
            boolean actualizar = false;
            
            if (!googleId.equals(usuario.getGoogleId())) {
                usuario.setGoogleId(googleId);
                actualizar = true;
            }
            
            if (!imagenUrl.equals(usuario.getImagenUrl())) {
                usuario.setImagenUrl(imagenUrl);
                actualizar = true;
            }
            
            if (actualizar) {
                usuarioService.update(usuario.getId(), usuario);
            }
        }
        
        return oauth2User;
    }
}