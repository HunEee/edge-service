package com.example.edgeservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

// 완전한 스프링 웹 애플리케이션 콘텍스트와 임의의 포트에서 듣는 웹 환경을 로드
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// 테스트테이너의 자동 시작과 종료를 활성화
@Testcontainers
class EdgeServiceApplicationTests {

	private static final int REDIS_PORT = 6379;

	// 테스트를 위한 레디스 컨테이너 정의
	@Container
	static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7.2"))
			.withExposedPorts(REDIS_PORT);
	
	// 테스트 인스턴스를 사용하도록 레디스 설정 변경
	@DynamicPropertySource
	static void redisProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.redis.host", () -> redis.getHost());
		registry.add("spring.data.redis.port", () -> redis.getMappedPort(REDIS_PORT));
	}

	// 애플리케이션 콘텍스트가 올바르게 로드되었는지, 레디스 연결이 성공적으로 됐는지 확인하기 위한 테스트로 비어 있다.
	@Test
	void verifyThatSpringContextLoads() {
	}

}
