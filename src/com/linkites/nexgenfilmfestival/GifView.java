package com.linkites.nexgenfilmfestival;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

 class GIFView extends View{
    	
    	Movie movie;
    	InputStream is=null,is1=null;
    	long moviestart;
		public GIFView(Context context) {
			super(context);
			}
		
		public GIFView(Context context,AttributeSet attributeSet){
			
			super(context, attributeSet);
			is=context.getResources().openRawResource(R.drawable.new_animated_splash);
			movie=Movie.decodeStream(is);
			movie.duration();
		}
		
    	@Override
    	protected void onDraw(Canvas canvas) {
    		canvas.drawColor(Color.TRANSPARENT);
    		super.onDraw(canvas);
    		long now=android.os.SystemClock.uptimeMillis();
    		System.out.println("now="+now);
    		 if (moviestart == 0) {   // first time
                 moviestart = now;
                 
             }
    		
    		 System.out.println("\tmoviestart="+moviestart);
    		 int relTime = (int)((now - moviestart) % movie.duration()) ;
    		
    		 System.out.println("time="+relTime+"\treltime="+movie.duration());
             movie.setTime(relTime);
             movie.draw(canvas,00,00);
             this.invalidate();
    	}
    }