package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestBody {
    private String name;
    private String job;
}
