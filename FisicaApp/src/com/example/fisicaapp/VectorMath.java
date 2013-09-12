package com.example.fisicaapp;


class VectorMath{ //OJO : VALIDO PARA COORDENADAS REALES!

	private double  angulo, magnitud; //angulo: -1 en el origen, [0,2*pi] en radianes!
	private double coordX, coordY;  // POSICIONES RELATIVAS A LA TRASLACION EN EL CENTRO DE LA PANTALLA
	/*
	Coordenadas del Origen
	*/
	private int origenX, origenY;
	/*
	 Coordenadas para las flechas internamente estara respecto a la traslacion
	 pero se obtendran con sus get() respectivos de manera real (sin traslacion) para efectos de graficar
	*/
	private double flechaIzqX, flechaIzqY, flechaDerX, flechaDerY; 

	private int cuadrante;  // 1: cuadrante I, 2: cuadrante II, 3: cuadrante III, 4: cuadrante IV, 0: entre 2 cuadrantes o el origen
	private int ancho, alto; // tamano del frame
	
	
// CONSTRUCTURES
	public VectorMath (double coordX,double coordY, int ancho, int alto){ ////ancho y alto del frame (disp. movil)
		
		this.origenX = (int)ancho/2;
		this.origenY = (int)alto/2;
		this.ancho = ancho;
		this.alto = alto;
		
		/* trasladando el sistema de coordenadas al centro de la pantalla 
		NOTA: estando el 0,0 en esquina sup-izq y considerando las coordenadas obtenidas solo del "cuadrante  I" del frame
		la traslacion generara los 4 diferentes cuadrantes.	

		*/
		this.coordX = coordX - origenX;
		this.coordY = (-1)*(coordY- origenY); // coordenada en Y invertida
		setACMF();
		
		
			
	}

	// Inicializacion sin traslacion
	/*
	USAR SOLO SI NO SE REQUIERE DE TRASLACION

	*/
	public VectorMath(double coordX,double coordY){ //ancho y alto del frame (disp. movil)
		this.coordX = coordX;
		this.coordY = coordY;
		this.origenX = 0;
		this.origenY = 0;
		this.ancho = 0;
		this.alto = 0;		
		setACMF();

			
	}
	
	// Inicializacion con traslacion y vector unitario en eje X

/*
	Cuidar de castear al inicializar para no entrar en conflicto con el constructor anterior!
*/
	public VectorMath(int ancho, int alto){ //ancho y alto del frame (disp. movil)
		
		this.origenX = (int)ancho/2;
		this.origenY = (int)alto/2;
		this.coordX = 0;
		this.coordY = 0;
		this.ancho = ancho;
		this.alto = alto;
		/* trasladando el sistema de coordenadas al centro de la pantalla 
		NOTA: estando el 0,0 en esquina sup-izq y considerando las coordenadas obtenidas solo del "cuadrante  I" del frame
		la traslacion generara los 4 diferentes cuadrantes.	
		*/
		this.coordX = this.coordX - this.origenX;
		this.coordY = (-1)*(this.coordY- this.origenY); // coordenada en Y invertida (debido al disp. movil que obtiene la coordenada asi)
		setACMF();

			
	} // FIN CONSTRUCTORES DE LA CLASE

	/*
	*********  METODOS DE ACCESO ************
	*/

	public double getAngulo(){ return angulo; }
	public double getAnguloGrados(){ return  angulo*(180/Math.PI); }
	public double getOrigenX(){ return origenX; }
	public double getOrigenY(){ return origenY; }
	public double getX(){ return coordX; }	
	public double getY(){ return coordY; }
	public int getAncho(){ return ancho; }
	public int getAlto(){ return alto; }
	public double getMagnitud(){ return magnitud; }
	public int getCuadrante(){ return cuadrante; }

	//Valores X e Y sin traslacion
	public double getXReal(){ return (this.coordX + this.origenX); }
	public double getYReal(){ return (this.origenY - this.coordY) ; }

	// Acceso a las punta de la flecha
	public double getFlechaIzqXReal(){ return (this.flechaIzqX + this.origenX); }
	public double getFlechaIzqYReal(){ return (this.origenY - this.flechaIzqY); }	
	public double getFlechaDerXReal(){ return (this.flechaDerX + this.origenX); }
	public double getFlechaDerYReal(){ return (this.origenY - this.flechaDerY); }


	 /* 
	 ********** METODOS PARA OPERAR CON VECTORES *************
	 */

	/*Suma un  vector, el resultante queda en el vector de esta clase!! 
	*/

	public void sumaVector(VectorMath vect){ // Para restar vectores usar metodo setVectorOpuesto()
		this.coordX =this.coordX + vect.getX();
		this.coordY = this.coordY + vect.getY();
		setACMF();
	}

	/*Retornar el vector resultante! usando el vector de la clase mas otro de parametro!
	**** Metodo propio de la clase , no del objeto! ******
	*/
	public static VectorMath sumarVectores(VectorMath vect1, VectorMath vect2){ // Para restar vectores usar metodo setVectorOpuesto()
		
		double X = vect1.getX() + vect2.getX();
		double Y = vect1.getY() + vect2.getY();
		VectorMath v = new VectorMath(X,Y,vect1.getAncho(),vect1.getAlto());
		return v;
	}


	public double getProductoPunto(VectorMath vect){
		double pp = this.getX()*vect.getX() + this.getY()*vect.getY();
		return pp;
	}
	public double getMagnitudProductoCruz(VectorMath vect){
		double pc = Math.abs(this.getX()*vect.getY() - this.getY()*vect.getX());
		return pc;
	}

	public double getAnguloVectores(VectorMath vect){ // usando relacion de coseno entre vectores		
		double angle =  (Math.acos( (getProductoPunto(vect) / (this.getMagnitud() * vect.getMagnitud() ) )));
		angle = angle*(180/(Math.PI)); // cambio a grados
		return angle;

	}
	public boolean isOrtogonal(VectorMath vect){
		if (this.getProductoPunto(vect) == 0 ){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean isColineal(VectorMath vect){
		if (this.getMagnitudProductoCruz(vect) == 0){
			return true;
		}
		else{
			return false;
		}

	}
	public double getCoeficienteColineal(VectorMath vect){
		if (this.isColineal(vect)){
			double angle = this.getAnguloVectores(vect);
			double alpha = this.getMagnitud() / vect.getMagnitud();
			if (angle != 0){ // vectores opuestos
				alpha = (-1)*(alpha);
			}
				
			return alpha;
			

		}else{ return 0; }  // no son colineales.
	}
	
	


	//METODOS PARA CAMBIAR LOS ATRIBUTOS
	public void setDefaultXY(){
		this.coordX = 0;
		this.coordY = 0;
		setACMF();
	}
	/*
	Usar en caso de cambios en la pantalla
	ejemplo cambiar de Portrait a Landscape por girar el dispositivo
	*/
	public void setOrigen(int ancho,int alto){
		this.origenX = (int)ancho/2;
		this.origenY = (int)alto/2;
		this.ancho = ancho;
		this.alto = alto;
	}
	public void  setX(double coordX){
		this.coordX = coordX - this.origenX; //trasladando al sistema de coordenadas
		setACMF();
	}
	
	public void setY(double coordY){
		this.coordY = (-1)*(coordY - this.origenY); //trasladando al sistema de coordenadas
		setACMF();
	
	}
	public void setXY(double X, double Y){
		this.coordX = X-this.origenX; //trasladando al sistema de coordenadas
		this.coordY = (-1)*(Y-this.origenY); //trasladando al sistema de coordenadas
		setACMF();
	}
	public void setVectorOpuesto(){ //vector opuesto
		this.coordX = (-1)*this.coordX;
		this.coordY = (-1)*this.coordY;
		setACMF();
	}

	// metodo pendiente si se usara...
	public void setPrimerCuadrante(){
		
		switch (cuadrante){ //caso 1 se obvia... ya esta en el primer cuadrante
			case 0:
				if (this.coordX != 0){
					this.coordX = Math.abs(this.coordX);
				}
				else if(this.coordY != 0){
					this.coordX = Math.abs(this.coordY);
				}
			 	this.coordY = 0;

			break;
			case 2:
				this.coordX = (-1)*this.coordX;				
			break;
			case 3:
				setVectorOpuesto();
			break;
			case 4:				
				this.coordY = (-1)*this.coordY;
			break;			

		}
		setACMF();

	}
	public void setAngulo(double ang){ //angulo en radianes
		if (ang > 2*Math.PI){
			int veces = (int) (ang / (2*Math.PI));
			ang -= veces*(2*Math.PI); //quito todas las posibles vueltas
		}
		this.angulo = ang;		
		this.coordX = ( Math.cos(ang))*(this.getMagnitud());
		this.coordY = ( Math.sin(ang))*(this.getMagnitud());
	}



	/*
		Genera la punta de la flecha para un vector en particular
		Rotando el punto con que finaliza el vector respecto al origen y luego se reduce su tamaño
		
	*/
	public void setFlechas(){
		/*double angulo = (0.6632*30)/this.magnitud;		
		double reduccion = 1.10;
		//double angulo = this.angulo + 3*Math.PI/4;
		//double reduccion = 0.1*this.magnitud;


		this.flechaIzqX = ( Math.cos(angulo)*(this.coordX) - Math.sin(angulo)*(this.coordY) )/reduccion; 	
		this.flechaIzqY =( Math.sin(angulo)*(this.coordX) + Math.cos(angulo)*(this.coordY) )/reduccion; 

		this.flechaDerX = ( Math.cos(-angulo)*(this.coordX) - Math.sin(-angulo)*(this.coordY) )/reduccion; 	
		this.flechaDerY = ( Math.sin(-angulo)*(this.coordX) + Math.cos(-angulo)*(this.coordY) )/reduccion; 
		//funciona! */
		double angulo = Math.PI/40;
		double gamma = (Math.PI - angulo)/2;
		double p = Math.PI/4 - gamma;
		double q = Math.PI - (p+gamma);
		//ROTACION RESPECTO A angulo
		this.flechaIzqX = ( Math.cos(angulo)*(this.coordX) - Math.sin(angulo)*(this.coordY) ); 	
		this.flechaIzqY =( Math.sin(angulo)*(this.coordX) + Math.cos(angulo)*(this.coordY) ); 

		this.flechaDerX = ( Math.cos(-angulo)*(this.coordX) - Math.sin(-angulo)*(this.coordY) ); 	
		this.flechaDerY = ( Math.sin(-angulo)*(this.coordX) + Math.cos(-angulo)*(this.coordY) ); 

		double reduccion;
		if (this.magnitud < 40){
			reduccion = 0;			
		}else
		{
			 reduccion= (Math.sin(p)/Math.sin(q)) * (Math.sqrt( Math.pow( this.coordX - this.flechaIzqX , 2) + Math.pow(this.coordY - this.flechaIzqY, 2) ));
		}
		this.flechaIzqX /= 1-(reduccion/this.magnitud);
		this.flechaIzqY /= 1-(reduccion/this.magnitud);
		this.flechaDerX /= 1-(reduccion/this.magnitud);
		this.flechaDerY /= 1-(reduccion/this.magnitud);

		
	}
	

	//Cambia el angulo, el cuadrante, magnitud y flechas del vector!
	public void setACMF(){ 
			
		
		this.angulo = -1;	//supuesto que este en el origen 
		this.magnitud = (Math.sqrt( Math.pow( this.getX(), 2) + Math.pow( this.getY(), 2)));
		if (coordX == 0){ // 2 opciones para Y (forma parte del eje Y)
			this.cuadrante = 0;
			if (coordY < 0){
				this.angulo =  (3*(Math.PI)/2);
			}
			else if (coordY > 0){
				this.angulo = ((Math.PI)/2);
			}
		}

		else if (coordY == 0){ // 2 opciones para X (forma parte del eje X)
			this.cuadrante = 0;
			if (coordX < 0){
				this.angulo = (Math.PI);
			}
			else if (coordX > 0){
				this.angulo = 0;

			}
			
		}

		else if(coordX!=0 && coordY!=0){			
			
			if (coordY > 0){				
				if (coordX>0){ //primer cuadrante, angulo ya calculado...
					this.cuadrante = 1;
					this.angulo = Math.atan(Math.abs( ((double)coordY/coordX))); //ANGULO EN PRIMER CUADRANTE
				}
				else if(coordX<0){ //segundo cuadrante
					this.cuadrante = 2;
					this.angulo =  Math.atan(Math.abs( ((double)coordX/coordY))); //ANGULO OPUESTO EN SEGUNDO CUADRANTE
					this.angulo =  (angulo + (Math.PI/2));
				}
				
			}
			else{
				
				if (coordX<0){ //tercer cuadrante
					this.cuadrante = 3;
					this.angulo =  Math.atan(Math.abs(((double)coordY/coordX))); //ANGULO EN TERCER CUADRANTE
					this.angulo = (angulo + (Math.PI));
				}
				else if(coordX>0){ //cuarto cuadrante
					this.cuadrante = 4;
					this.angulo =  Math.atan(Math.abs(((double)coordX/coordY))); //ANGULO OPUESTO EN CUARTO CUADRANTE
					this.angulo =  (angulo + (3*Math.PI/2));

				}
				
			}
			
		}
		setFlechas();
	}		
}