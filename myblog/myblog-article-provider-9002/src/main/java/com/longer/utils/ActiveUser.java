package com.longer.utils;

import com.longer.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
public class ActiveUser implements Serializable {
    private User user;
    private List<String> roles;
    private List<String> permissions;
}
