package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;

class TestToken {

	@Test
	void test() {
		Token token = new Token(TokenType.ASSIGN, 1, null);
		assertNotNull(token);
		assertNotNull(token.getType());
		assertNotNull(token.getLine());
		assertNull(token.getValue());
		
		Token token2 = new Token(TokenType.ASSIGN, 1, null);
		assertEquals(token, token2);
	}
}
