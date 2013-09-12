package com.example.fisicaapp;




import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Actvector extends Activity implements OnTouchListener {
	private VectorMath vect1;
	private VectorMath vect2;
	private VectorMath vsuma;
	
	int x,y; // ORIGEN
    private int ancho, alto;    
    private int cont2 = 0;
    private panel fondo;
    private ToggleButton toggle , toggle2;
    private TextView vmagnitud1 ;

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.appvector);

        // ubicacion de los puntos en un comienzo
        vmagnitud1 = (TextView) findViewById(R.id.textmagnitud1);
        
        
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layou2); 
        ancho=(int) (getWindowManager().getDefaultDisplay().getWidth()); 
        alto=(int) (getWindowManager().getDefaultDisplay().getHeight());
        vect2 = new VectorMath(ancho,alto);
        vect1 = new VectorMath(ancho,alto);
       
        
        vsuma = new VectorMath(ancho,alto);
        x = (int) vect1.getOrigenX();
        y = (int) vect1.getOrigenY();
       
        
        
        fondo = new panel(this);;
        fondo.setOnTouchListener(this);
        
        layout.addView(fondo);
        
        

        toggle = (ToggleButton) findViewById(R.id.vect1);
        toggle2 = (ToggleButton) findViewById(R.id.vect2);
/*        
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    toggle2.setChecked(false);
                     cont2=1;
                } else {
                    cont2=5;
                }
            }
        });
        
        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    toggle.setChecked(false);
                     cont2=3;

                } else {
                    cont2=5;
                }
            }
        });

     */
        
     
    }
    public void volver(View view) {
       finish();
  }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onTouch(View v, MotionEvent event) {    	
    	    	
    	//veo distancias al cuadrado
    	double X = event.getX();
    	double Y = event.getY();   		    	
    	double distancia1, distancia2;
    	
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    	
        	
        	
        	distancia1 = Math.pow( X - vect1.getXReal() ,2) + Math.pow( Y - vect1.getYReal() ,2) ;
        	distancia2 = Math.pow( X - vect2.getXReal() ,2) + Math.pow( Y - vect2.getYReal() ,2) ;
        	      	
    		if (distancia1>distancia2){
        		cont2=1;
        	}else{
        		cont2=0;
        	}
    		if( cont2==1){
                vect1.setXY(X,Y);                        
                fondo.invalidate();
               
               
            	}
        	
        	else if( cont2==0){
        		vect2.setXY(X,Y);    		
        		fondo.invalidate();
              
            	}
    		
    	}else if(event.getAction() == MotionEvent.ACTION_MOVE){
    		if( cont2==1){
                vect1.setXY(X,Y);                        
                fondo.invalidate();
               
               
            	}
        	
        	else if( cont2==0){
        		vect2.setXY(X,Y);    		
        		fondo.invalidate();
              
            	}
    		Log.d("as","PASo POR AQUI?");
    	}else if(event.getAction() == MotionEvent.ACTION_UP){
    		
    		cont2=-1;
    		
    		
    	}
    	
   
        return true;
    }
    
    
   

    
    //Panel para pintar!
    class panel extends View {
    	
    	Paint pincel1, pincel3;
        
    
        public panel(Context context) {
            super(context);
            pincel1 = new Paint();
            pincel3 = new Paint();
            // color del fondo
        	//canvas.drawRGB(255, 255, 0); 
        	// cada linea es un pincel
            
            // atributos de los pinceles
            pincel1.setARGB(255, 255, 0, 0);
            pincel1.setStrokeWidth(4);
            pincel1.setStyle(Paint.Style.STROKE);
            
            pincel3.setARGB(255, 0, 255, 0);
            pincel3.setStrokeWidth(4);
            pincel3.setStyle(Paint.Style.STROKE);
        }

        protected void onDraw(Canvas canvas) {
        	
            // dibujar lineas
            
            vmagnitud1.setText("Vector 1 :  Magnitud: "+ (int)vect1.getMagnitud() +" Angulo : "+(short)vect1.getAnguloGrados()+"º"+ Html.fromHtml("<br />") +
            		"Xr "+(int)vect1.getXReal()+" Yr "+(int)vect1.getYReal()+ Html.fromHtml("<br />") +
            		"Vector 2 :  Magnitud: "+ (int)vect2.getMagnitud() +" Angulo : "+(short)vect2.getAnguloGrados()+"º"+ Html.fromHtml("<br />") +
            		"Vector suma: Magnitud: "+(int)vsuma.getMagnitud()+"Angulo :"+(short)vsuma.getAnguloGrados()+"º"+ Html.fromHtml("<br />") + 
            		"Angulo entre Vector 1 y 2 :"+ (short) vect1.getAnguloVectores(vect2) +"º");
            
           
            vsuma.setDefaultXY();
            vsuma.sumaVector(vect1);
            vsuma.sumaVector(vect2);   
            
            
            canvas.drawLine(x, y,(int) vect1.getXReal() ,(int) vect1.getYReal(), pincel1);            
            canvas.drawLine((int) vect1.getXReal(), (int) vect1.getYReal(),
            		(int) vect1.getFlechaIzqXReal(),(int) vect1.getFlechaIzqYReal(),
            		pincel1);
            canvas.drawLine((int) vect1.getXReal(), (int) vect1.getYReal(),
            		(int) vect1.getFlechaDerXReal(),(int) vect1.getFlechaDerYReal(),
            		pincel1);
           
            canvas.drawLine(x, y, (int) vect2.getXReal(), (int) vect2.getYReal(), pincel1);
            canvas.drawLine((int) vect2.getXReal(), (int) vect2.getYReal(),
            		(int) vect2.getFlechaIzqXReal(),(int) vect2.getFlechaIzqYReal(),
            		pincel1);
            canvas.drawLine((int) vect2.getXReal(), (int) vect2.getYReal(),
            		(int) vect2.getFlechaDerXReal(),(int) vect2.getFlechaDerYReal(),
            		pincel1);
           
            canvas.drawLine(x, y, (int) vsuma.getXReal(), (int) vsuma.getYReal(), pincel3);
            canvas.drawLine((int) vsuma.getXReal(), (int) vsuma.getYReal(),
            		(int) vsuma.getFlechaIzqXReal(),(int) vsuma.getFlechaIzqYReal(),
            		pincel3);
            canvas.drawLine((int) vsuma.getXReal(), (int) vsuma.getYReal(),
            		(int) vsuma.getFlechaDerXReal(),(int) vsuma.getFlechaDerYReal(),
            		pincel3);
            
        }
        
        
        
    }
   

}