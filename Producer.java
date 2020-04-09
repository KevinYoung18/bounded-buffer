package app;

import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import app.HelloWorld.ChangeMessage;
import app.HelloWorld.SayHello;

public class Producer extends  AbstractBehavior<Producer.Command>
{
	public Producer(ActorContext<Command> context) 
	{
		super(context);
	}

	interface Command{}
	
	public static class StartConsuming implements Command
	{
		
	}

	
	@Override
	public Receive<Command> createReceive() 
	{
		return newReceiveBuilder()
				.onMessage(StartConsuming.class, this::onStartConsuming)
				.onMessage()
				.build();
	}
	
}
