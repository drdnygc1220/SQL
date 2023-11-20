package com.tpe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tpe.domain.Role;
import com.tpe.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class UserRequest {

    @NotBlank(message = "Please provide firstName")
    private String firstName;

    @NotBlank(message = "Please provide lastName")
    private String lastName;

    @NotBlank(message = "Please provide userName")
    private String userName;

    @NotBlank(message = "Please provide Password")
    private String password; // sifre hashlenirse 25 karakterden fazla olma ihtimali oldugu icin 255 karakter sinirina cekildi

}




