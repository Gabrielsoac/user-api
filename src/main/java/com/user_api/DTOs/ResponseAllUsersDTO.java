package com.user_api.DTOs;

import java.util.List;

public record ResponseAllUsersDTO (List<ResponseUserDTO> users) {
}
