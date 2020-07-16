package com.onlineinteract.workflow.bus;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import com.onlineinteract.workflow.domain.account.v1.Account;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

@Component
public class Producer {
	private KafkaProducer<String, Account> producer;

	public Producer() {
		Properties producerProps = buildProducerProperties();
		producer = new KafkaProducer<String, Account>(producerProps);
	}

	public void publishRecord(String topic, Account value, String key) {
		producer.send(new ProducerRecord<>(topic, key, value));
	}

	private Properties buildProducerProperties() {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "tiny.canadacentral.cloudapp.azure.com:29092");
		properties.put("key.serializer", StringSerializer.class);
		properties.put("value.serializer", KafkaAvroSerializer.class);
		properties.put("schema.registry.url", "http://tiny.canadacentral.cloudapp.azure.com:8081");
		return properties;
	}
}
