package com.employeeservice.controllers;

import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.models.Account;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//Current Class is not really controller ,and just a class for autogenration of swagge resource

@RestController
@RequestMapping(path = "/auth/")
@Api(value = "authentication", description = "Operations for authentication and authorization")
public class AuthController {


    @ApiOperation(value = "Authenticate/Authorize User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User Authorized/Authenticated Successfully", responseHeaders =
                    {@ResponseHeader(name = "Authorization", description = "Bearer jwt token", response = String.class)})
    }
    )
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> authMockMethod(@RequestBody Account department) throws EntityAlreadyExistsException {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}