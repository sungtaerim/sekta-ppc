package com.sekta.ppc.utils.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sekta.ppc.dao.entity.Student;
import com.sekta.ppc.dao.entity.UserToken;
import com.sekta.ppc.utils.http.dto.StudentPpcDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.hc.client5.http.fluent.Content;
import org.apache.hc.client5.http.fluent.Form;
import org.apache.hc.client5.http.fluent.Request;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class ClientRequestImpl implements ClientRequest {

	private final String urlLogin;
	private final String urlMessages;
	private final String urlStudents;

	@Override
	public String getToken(String login, String password) throws IOException {
		log.info("getToken(login = {})", login);
		return deserializeSingleResponse(Request.post(urlLogin)
				.bodyForm(Form.form()
					.add("email", login)
					.add("password", password)
					.build())
				.execute().returnContent());
	}


	@Override
	public Map<String, LocalDateTime> getMessages(String token) throws IOException {
		return deserializeMessageResponse(Request.get(urlMessages)
			.addHeader("Authorization", "Bearer " + token)
			.execute().returnContent());
	}


	@Override
	public StudentPpcDto getAllStudents(String token) {
//		val content = Request.get(urlStudents)
//				.addHeader("Authorization", "Bearer " + token)
//				.execute().returnContent();

		return new RestTemplate()
				.exchange(urlStudents, HttpMethod.GET, new HttpEntity<>(getHeaders(token)), StudentPpcDto.class)
				.getBody();
	}


	private String deserializeSingleResponse(Content response) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode result = objectMapper.readValue(response.toString(), ObjectNode.class);
		return result.get("token").asText();
	}


	private Map<String, LocalDateTime> deserializeMessageResponse(Content response) throws IOException {
		Map<String, LocalDateTime> messagesFrom = new HashMap<>();

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode data = objectMapper.readValue(response.toString(), ObjectNode.class);
		ObjectNode[] nodes = objectMapper.readValue(data.get("data").toString().replaceAll("\\[]", "").replaceAll("\"files\":.+?,", ""), ObjectNode[].class);

		for (ObjectNode node : nodes) {
			val messages = objectMapper.readValue(node.get("messages").toString().replaceAll("\\[]", ""), ObjectNode[].class);
			val time = LocalDateTime.parse(node.get("updated_at").asText().replace(" ", "T"));
			for (ObjectNode message : messages) {
				val sender = objectMapper.readValue(message.get("sender").toString(), ObjectNode.class);
				messagesFrom.put(sender.get("name").asText(), time);
			}
		}

		return messagesFrom;
	}

	private HttpHeaders getHeaders(String token) {
		val headers = new HttpHeaders();
		headers.setBearerAuth(token);
		return headers;
	}
}
