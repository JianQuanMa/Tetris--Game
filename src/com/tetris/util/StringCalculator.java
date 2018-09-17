package com.tetris.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class StringCalculator {
	static ScriptEngineManager mgr = new ScriptEngineManager();
	static ScriptEngine engine = mgr.getEngineByName("JavaScript");

	public static int cal(String str) throws ScriptException {
		String result = String.valueOf(engine.eval(str));
		if (result.contains(".")) {
			result = result.substring(0, result.indexOf('.'));
		}
		return Integer.valueOf(result);
	}

}
