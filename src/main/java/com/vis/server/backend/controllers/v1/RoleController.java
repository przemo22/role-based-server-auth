package com.vis.server.backend.controllers.v1;

import com.vis.server.backend.domain.dto.RoleDto;
import com.vis.server.backend.domain.dto.RoleResponseDto;
import com.vis.server.backend.domain.services.RolesService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/v1/vis-test/roles")
public class RoleController {

    private final RolesService rolesService;

    @Autowired
    public RoleController(final RolesService rolesService) {
        this.rolesService = rolesService;
    }


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        return new ResponseEntity<>(rolesService.getAllDefinedRoles(), HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertNewRole(@Valid @RequestBody RoleDto roleDto) {
        try {
            RoleResponseDto roleResponseDto = rolesService.insertRole(roleDto);
            return new ResponseEntity<>(roleResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateRole(@NotNull @PathVariable Long id,
        @Valid @RequestBody RoleDto roleDto) {
        try {
            RoleResponseDto roleResponseDto = rolesService
                .updateRole(id, roleDto);
            return new ResponseEntity<>(roleResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteRole(@NotNull @PathVariable Long id) {
        try {
            rolesService.deleteRole(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
