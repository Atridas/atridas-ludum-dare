package cat.atridas87.ld27;

public class Popup {

	public enum Type {
		START("resources/titleScreen.png"),
		FINISH("resources/finishScreen.png"),
		PAUSE("resources/pause.png"),
		TIPS("resources/tipsScreen.png"),
		TIPS_IN_GAME("resources/tipsScreen.png"),
		CREDITS("resources/credits.png"),
		YOU_SURE("resources/youSure.png");

		final public String textureName;

		Type(String _textureName) {
			textureName = _textureName;
		}
	}

	final Type type;

	final int numButtons;
	final boolean buttonPressed[];
	final int buttonX[], buttonY[], buttonW[], buttonH[], buttonS[], buttonT[],
			buttonSP[], buttonTP[];

	public Popup(Type _type) {
		type = _type;
		switch (type) {
		case START:

			numButtons = 4;
			break;
		case FINISH:
			
			numButtons = 1;
			break;
		case PAUSE:
			
			numButtons = 3;
			break;
		case TIPS:
			
			numButtons = 1;
			break;
		case TIPS_IN_GAME:
			
			numButtons = 1;
			break;
		case CREDITS:
			
			numButtons = 1;
			break;
		case YOU_SURE:
			
			numButtons = 2;
			break;
		default:
			numButtons = 0;
			break;
		}

		buttonPressed = new boolean[numButtons];
		buttonX = new int[numButtons];
		buttonY = new int[numButtons];
		buttonW = new int[numButtons];
		buttonH = new int[numButtons];
		buttonS = new int[numButtons];
		buttonT = new int[numButtons];
		buttonSP = new int[numButtons];
		buttonTP = new int[numButtons];

		switch (type) {
		case START:
			buttonX[0] = buttonX[1] = buttonX[2] = buttonX[3] = 220;
			buttonY[0] = 700 - 300;
			buttonY[1] = 700 - 400;
			buttonY[2] = 700 - 500;
			buttonY[3] = 700 - 600;

			buttonW[0] = buttonW[1] = buttonW[2] = buttonW[3] = 220;
			buttonH[0] = buttonH[1] = buttonH[2] = buttonH[3] = 60;

			buttonS[0] = 767;
			buttonT[0] = 77;
			buttonSP[0] = 767;
			buttonTP[0] = 176;

			buttonS[1] = 767;
			buttonT[1] = 303;
			buttonSP[1] = 767;
			buttonTP[1] = 411;

			buttonS[2] = 767;
			buttonT[2] = 541;
			buttonSP[2] = 767;
			buttonTP[2] = 644;

			buttonS[3] = 11;
			buttonT[3] = 777;
			buttonSP[3] = 11;
			buttonTP[3] = 885;

			break;
		case FINISH:
			buttonX[0] = 220;
			buttonY[0] = 700 - 600;

			buttonW[0] = 220;
			buttonH[0] = 60;

			buttonS[0] = 11;
			buttonT[0] = 777;
			buttonSP[0] = 11;
			buttonTP[0] = 885;
			
			break;
		case PAUSE:
			buttonX[0] = buttonX[1] = buttonX[2] = 220;
			buttonY[0] = 700 - 300;
			buttonY[1] = 700 - 400;
			buttonY[2] = 700 - 500;

			buttonW[0] = buttonW[1] = buttonW[2] = 220;
			buttonH[0] = buttonH[1] = buttonH[2] = 60;

			buttonS[0] = 767;
			buttonT[0] = 77;
			buttonSP[0] = 767;
			buttonTP[0] = 176;

			buttonS[1] = 767;
			buttonT[1] = 303;
			buttonSP[1] = 767;
			buttonTP[1] = 411;

			buttonS[2] = 767;
			buttonT[2] = 541;
			buttonSP[2] = 767;
			buttonTP[2] = 644;

			break;
		case TIPS:
			buttonX[0] = 220;
			buttonY[0] = 700 - 600;

			buttonW[0] = 220;
			buttonH[0] = 60;

			buttonS[0] = 11;
			buttonT[0] = 777;
			buttonSP[0] = 11;
			buttonTP[0] = 885;
			
			break;
		case TIPS_IN_GAME:
			buttonX[0] = 220;
			buttonY[0] = 700 - 600;

			buttonW[0] = 220;
			buttonH[0] = 60;

			buttonS[0] = 11;
			buttonT[0] = 777;
			buttonSP[0] = 11;
			buttonTP[0] = 885;
			
			break;
		case CREDITS:
			buttonX[0] = 220;
			buttonY[0] = 700 - 600;

			buttonW[0] = 220;
			buttonH[0] = 60;

			buttonS[0] = 11;
			buttonT[0] = 777;
			buttonSP[0] = 11;
			buttonTP[0] = 885;
			
			break;
		case YOU_SURE:
			buttonX[0] = buttonX[1] = 220;
			buttonY[0] = 700 - 300;
			buttonY[1] = 700 - 400;

			buttonW[0] = buttonW[1] = 220;
			buttonH[0] = buttonH[1] = 60;

			buttonS[0] = 767;
			buttonT[0] = 77;
			buttonSP[0] = 767;
			buttonTP[0] = 176;

			buttonS[1] = 767;
			buttonT[1] = 303;
			buttonSP[1] = 767;
			buttonTP[1] = 411;

			break;
		}
	}

	int coordsToButton(int x, int y) {
		for (int b = 0; b < numButtons; b++) {
			if (x > buttonX[b] && y > buttonY[b] && x < buttonX[b] + buttonW[b]
					&& y < buttonY[b] + buttonH[b]) {
				return b;
			}
		}
		return -1;
	}

	void executarBoto(LD27 ld27, int boto) {
		switch(type) {
		case START:
			switch(boto) {
			case 0:
				ld27.popup = null;
				ld27.terrenyDeJoc = new TerrenyDeJoc();
				break;
			case 1:
				ld27.popup = null;
				ld27.terrenyDeJoc = new TerrenyDeJoc();
				ld27.terrenyDeJoc.timedMode = true;
				break;
			case 2:
				ld27.popup = new Popup(Type.TIPS);
				break;
			case 3:
				ld27.popup = new Popup(Type.CREDITS);
				break;
			}
			break;
		case FINISH:
			ld27.popup = new Popup(Type.START);
			break;
		case PAUSE:
			switch(boto) {
			case 0:
				ld27.popup = null;
				break;
			case 1:
				ld27.popup = new Popup(Type.YOU_SURE);
				break;
			case 2:
				ld27.popup = new Popup(Type.TIPS_IN_GAME);
				break;
			}
			break;
		case TIPS:
			ld27.popup = new Popup(Type.START);
			break;
		case TIPS_IN_GAME:
			ld27.popup = new Popup(Type.PAUSE);
			break;
		case CREDITS:
			ld27.popup = new Popup(Type.START);
			break;
		case YOU_SURE:
			switch (boto) {
			case 0:
				ld27.popup = new Popup(Type.START);
				break;
				
			case 1:
				ld27.popup = new Popup(Type.PAUSE);
				break;

			default:
				break;
			}
			break;
		}
	}
}
