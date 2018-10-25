package spark.utils;

import spark.template.handlebars.HandlebarsTemplateEngine;
import tipoDispositivo.ConsumoEnFecha;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.HumanizeHelper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.helper.I18nHelper;
import com.github.jknack.handlebars.helper.NumberHelper;
import com.github.jknack.handlebars.helper.StringHelpers;

import cliente.Cliente;
import repositorio.RepoConsumoEnFecha;

public class HandlebarsTemplateEngineBuilder {

	private HandlebarsTemplateEngine engine;

	private HandlebarsTemplateEngineBuilder(HandlebarsTemplateEngine engine) {
		this.engine = engine;
	}

	public static HandlebarsTemplateEngineBuilder create() {
		return new HandlebarsTemplateEngineBuilder(new HandlebarsTemplateEngine());
	}

	public HandlebarsTemplateEngineBuilder withHelper(String name, Helper<?> helper) {
		getHandlerbars().registerHelper(name, helper);
		return this;
	}

	private Handlebars getHandlerbars() {
		return (Handlebars) DarkMagic.getField(this.engine, "handlebars");
	}

	public HandlebarsTemplateEngine build() {
		return engine;
	}

	public HandlebarsTemplateEngineBuilder withDefaultHelpers() {
		StringHelpers.register(getHandlerbars());
		NumberHelper.register(getHandlerbars());
		HumanizeHelper.register(getHandlerbars());
		withHelper("i18n", I18nHelper.i18n);
		
		/*Handlebars handlebars = new Handlebars();
		handlebars.registerHelper("consumo", new Helper<Cliente>() {
			
			@Override
			public Object apply(Cliente cliente, Options arg1) throws IOException {
				//LocalDateTime desde = formatearFecha(req.queryParams("desde"), LocalTime.of(0, 0, 0, 0));
				//LocalDateTime hasta = formatearFecha(req.queryParams("hasta"), LocalTime.of(23, 59, 59, 999));
				Double consumo =RepoConsumoEnFecha.getInstance()
						.obtenerConsumoDeClienteEnFecha(cliente, (LocalDateTime)arg1.param(0),(LocalDateTime) arg1.param(1));
				System.out.println("Consumo del cliente " + cliente.getNombre() + " = " + consumo);
				return consumo;
			}
			});*/
		/*Helper<Cliente> helperConsumoEnFecha = new Helper<Cliente>() {
			@Override
			public Object apply(Cliente cliente, Options arg1) throws IOException {
				//LocalDateTime desde = formatearFecha(req.queryParams("desde"), LocalTime.of(0, 0, 0, 0));
				//LocalDateTime hasta = formatearFecha(req.queryParams("hasta"), LocalTime.of(23, 59, 59, 999));
				Double consumo = RepoConsumoEnFecha.getInstance()
						.obtenerConsumoDeClienteEnFecha(cliente, (LocalDateTime)arg1.param(0),(LocalDateTime) arg1.param(1));
				
			
				
				System.out.println("Consumo del cliente " + cliente.getNombre() + " = " + consumo);
				return consumo;
			}
		};
		withHelper("consumo", helperConsumoEnFecha);*/
		
		
		
		
		return this;
	}
	
	
	
}
