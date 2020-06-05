package com.rabbitmq.manager.config;

import com.rabbitmq.manager.factory.BlenderReceiver;
import com.rabbitmq.manager.factory.CoffeeReceiver;
import com.rabbitmq.manager.factory.NormalReceiver;
import com.rabbitmq.manager.rabbitmq_jieun.Manager;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannelImpl;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration

public class RabbitConfiguration {
	private static final String COFFEE_QUEUE_NAME = "coffee-queue";
	private static final String BLENDER_QUEUE_NAME = "blender-queue";
	private static final String NORMAL_QUEUE_NAME = "normal-queue";
	private static final String DIRECT_EXCHANGE_NAME = "direct-exchange";

	@Bean
	public Manager manager() {
		return new Manager();
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(DIRECT_EXCHANGE_NAME);
	}

	@Bean
	public Queue coffeeQueue() {
		return new Queue(COFFEE_QUEUE_NAME);
		// durable 브로커가 재시작 할 때 남아있는지 여부
	}
	@Bean
	public Queue blenderQueue() {
		return new Queue(BLENDER_QUEUE_NAME);
		// durable 브로커가 재시작 할 때 남아있는지 여부
	}

	@Bean
	public Queue normalQueue() {
		return new Queue(NORMAL_QUEUE_NAME);
	}

	@Bean
	public Binding bindingWithCoffeeQueue(Queue coffeeQueue, DirectExchange exchange) {
		return BindingBuilder.bind(coffeeQueue()).to(exchange).with("coffee");
	}
	@Bean
	public Binding bindingWithBlenderQueue(Queue blenderQueue, DirectExchange exchange) {
		return BindingBuilder.bind(blenderQueue()).to(exchange).with("blender");
	}

	@Bean
	Binding bindingWithNormalQueue(Queue normalQueue, DirectExchange exchange) {
		return BindingBuilder.bind(normalQueue()).to(exchange).with("normal");
	}

	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}


	@Bean
	public RabbitAdmin rabbitAdmin(RabbitTemplate template){


		return new RabbitAdmin(template);
	}

	@Bean
	public RabbitListenerContainerFactory<SimpleMessageListenerContainer> prefetchTenRabbitListenerContainerFactory(ConnectionFactory rabbitConnectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(rabbitConnectionFactory);
		factory.setPrefetchCount(1);
		factory.setConcurrentConsumers(1);
		//factory.setMaxConcurrentConsumers(5);
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		return factory;
	}
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
		return rabbitTemplate;
	}




}
