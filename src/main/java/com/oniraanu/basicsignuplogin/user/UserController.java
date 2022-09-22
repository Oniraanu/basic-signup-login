package com.oniraanu.basicsignuplogin.user;

import com.oniraanu.basicsignuplogin.dto.request.RegisterRequest;
import com.oniraanu.basicsignuplogin.dto.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity <?> register (@RequestBody RegisterRequest registerRequest){
        try {
            var serviceResponse = userService.register(registerRequest);
            ApiResponse apiResponse = new ApiResponse(true, serviceResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception ex){
            ApiResponse apiResponse = new ApiResponse(false, ex.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam String token){
        return userService.confirmToken(token);
    }
}
