package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data

public class GenerateTokenResponseModel {
    String token, expires, status, result;
}
