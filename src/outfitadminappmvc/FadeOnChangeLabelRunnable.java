package outfitadminappmvc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JLabel;

/**
 * Deze klasse zorgt er voor dat je een nieuw soort JLabel kan gebruiken in de design view
 * van Netbeans. Deze JLabel heeft een paar extra parameters die er voor zorgen
 * dat de JLbale kan infaden.
 * @author vincent
 */
public class FadeOnChangeLabelRunnable
extends JLabel implements Runnable{
	private String lastValue = "";
	private boolean animating = false;
	private float fade = 0;
	private float initFade = 0.5f;
	private float fadeStep = -0.030f;
	private float red = 0.2f;
	private float green = 0.2f;
	private float blue = 1.0f;
	private Color fadeColor = new Color(red,green,blue,fade);
	private boolean paintCalled = false;
	private Container repaintCont = null;
	public FadeOnChangeLabelRunnable(){
		setOpaque(true);
	}
	
	public void setParams(float r, float g, float b,
							float fStep, float iFade){
		if(r >= 0 && r <= 1)		red = r;
		if(g >= 0 && g <= 1)		green = g;
		if(b >= 0 && b <= 1)		blue = b;
		if(fStep > 0 && fStep < 1)	fadeStep = fStep;
		if(iFade >= 0 && iFade <= 1)initFade = iFade;
	}
	
	
	public void setRepaintContainer(Container c){
		repaintCont = c;
	}

	public void setText(String text){
		super.setText(text);
		if (!text.equals(lastValue)){
			lastValue = text;
			animate();
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(fade != 0){
			Insets i = this.getInsets();
			g.setColor(fadeColor);
			g.fillRect(i.left, i.top, 
					getWidth()-i.left-i.right, 
					getHeight()-i.top-i.bottom);
		}
		paintCalled = true;
	}

	private void animate() {
		if(!animating && this.getParent()!=null){
			Thread t = new Thread(this);
			t.start();
		}
	}
	
	public void run() {
		animating = true;
		fade = initFade;
		try {
			while (fade!=0) {
				fadeColor = new Color(red,green,blue,fade);
				fade += fadeStep;
				if (fade < 0) {
					fade = 0;
				}
				paintCalled = false;
				if(repaintCont == null) 
					repaint();
				else 
					repaintCont.repaint(); 
				while(!paintCalled && fade!=0){
					Thread.sleep(100);
				}
			}
			animating = false;
		}
		catch (Exception e) {
			animating = false;
			System.out.println("FadeOnChangeLabel encountered an error: "
					+ e.getMessage());
		}
	}

}

