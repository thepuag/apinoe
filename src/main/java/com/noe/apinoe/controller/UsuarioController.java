package com.noe.apinoe.controller;

import com.noe.apinoe.dto.ApiResponse;
import com.noe.apinoe.dto.UsuarioDto;
import com.noe.apinoe.mapper.UsuarioMapper;
import com.noe.apinoe.model.Usuario;
import com.noe.apinoe.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioDto>>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        List<UsuarioDto> usuariosDto = usuarios.stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success(usuariosDto));
    }

    @GetMapping("/activos")
    public ResponseEntity<ApiResponse<List<UsuarioDto>>> getUsuariosActivos() {
        List<Usuario> usuarios = usuarioService.findActivos();
        List<UsuarioDto> usuariosDto = usuarios.stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success(usuariosDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDto>> getUsuarioById(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        
        if (usuario.isPresent()) {
            UsuarioDto usuarioDto = usuarioMapper.toDto(usuario.get());
            return ResponseEntity.ok(ApiResponse.success(usuarioDto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Usuario no encontrado con id: " + id));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UsuarioDto>> getUsuarioByEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.findByEmail(email);
        
        if (usuario.isPresent()) {
            UsuarioDto usuarioDto = usuarioMapper.toDto(usuario.get());
            return ResponseEntity.ok(ApiResponse.success(usuarioDto));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Usuario no encontrado con email: " + email));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<UsuarioDto>>> buscarUsuarios(@RequestParam String nombre) {
        List<Usuario> usuarios = usuarioService.findByNombreContaining(nombre);
        List<UsuarioDto> usuariosDto = usuarios.stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(ApiResponse.success(usuariosDto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioDto>> createUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        try {
            // Convierte DTO a entidad
            Usuario usuario = usuarioMapper.toEntity(usuarioDto);
            
            // Guarda en base de datos
            Usuario nuevoUsuario = usuarioService.save(usuario);
            
            // Convierte la entidad guardada de vuelta a DTO
            UsuarioDto nuevoUsuarioDto = usuarioMapper.toDto(nuevoUsuario);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Usuario creado exitosamente", nuevoUsuarioDto));
                    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Error al crear usuario: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDto>> updateUsuario(@PathVariable Integer id,
                                                                @Valid @RequestBody UsuarioDto usuarioDto) {
        try {
            // Busca el usuario existente
            Optional<Usuario> usuarioExistente = usuarioService.findById(id);
            
            if (usuarioExistente.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Usuario no encontrado con id: " + id));
            }
            
            // Actualiza la entidad existente con datos del DTO
            Usuario usuario = usuarioExistente.get();
            usuarioMapper.updateEntityFromDto(usuario, usuarioDto);
            
            // Guarda los cambios
            Usuario usuarioActualizado = usuarioService.update(id, usuario);
            
            // Convierte de vuelta a DTO
            UsuarioDto usuarioActualizadoDto = usuarioMapper.toDto(usuarioActualizado);
            
            return ResponseEntity.ok(ApiResponse.success("Usuario actualizado exitosamente", usuarioActualizadoDto));
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Usuario no encontrado con id: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Error al actualizar usuario: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<ApiResponse<String>> activarUsuario(@PathVariable Integer id) {
        try {
            usuarioService.activar(id);
            return ResponseEntity.ok(ApiResponse.success("Usuario activado exitosamente", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Usuario no encontrado con id: " + id));
        }
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<ApiResponse<String>> desactivarUsuario(@PathVariable Integer id) {
        try {
            usuarioService.desactivar(id);
            return ResponseEntity.ok(ApiResponse.success("Usuario desactivado exitosamente", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Usuario no encontrado con id: " + id));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUsuario(@PathVariable Integer id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.ok(ApiResponse.success("Usuario eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al eliminar usuario: " + e.getMessage()));
        }
    }
}