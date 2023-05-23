package com.programming.karaoke.model.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    public Integer id;
    public String token;
    public boolean revoked;
    public boolean expired;
    @Field(targetType = FieldType.STRING)
    public TokenType tokenType = TokenType.BEARER;
}
