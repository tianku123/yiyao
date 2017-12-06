<%@ page contentType="image/jpeg" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.awt.image.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.imageio.*" %>
<%!
  Color getRandColor(int StartNumC,int EndNumC)
        {
          Random random = new Random();
          if(StartNumC>255) StartNumC=255;
          if(EndNumC>255) EndNumC=255;
          int r=StartNumC+random.nextInt(EndNumC-StartNumC);
          int g=StartNumC+random.nextInt(EndNumC-StartNumC);
          int b=StartNumC+random.nextInt(EndNumC-StartNumC);
          return new Color(r,g,b);
        }
%>

<%

  out.clear();
  response.reset();
  response.setContentType("image/jpeg"); 

  response.setHeader("Pragma","No-cache");
  response.setHeader("Cache-Control","no-cache");
  response.setDateHeader("Expires", 0);

  int width=44, height=18;  
  BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

  Graphics g = image.getGraphics();

  Random random = new Random();

  g.setColor(getRandColor(200,250));
  g.fillRect(0, 0, width, height);

  g.setFont(new Font("Times New Roman",Font.BOLD,18));
  
  g.setColor(getRandColor(160,200));
  for (int i=0;i<80;i++)
  {
	  int x = random.nextInt(width);
	  int y = random.nextInt(height);
      int xl = random.nextInt(12);
      int yl = random.nextInt(12);
	  g.drawLine(x,y,x+xl,y+yl);
  }

  String sRand="";
  int RandNum=15;
  int UpNum=18;
  int DownNum=14;
  RandNum=random.nextInt(10);
  for (int i=0;i<4;i++){
      String rand=String.valueOf(random.nextInt(10));
      sRand+=rand;
      g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
	  //g.setColor(Color.BLACK);
      if(RandNum%2==0){
		  if((i==1)||(i==3)){g.drawString(rand,10*i+4,UpNum);
		  }else{g.drawString(rand,10*i+4,DownNum);}
	  }else{
		  if((i==1)||(i==3)){g.drawString(rand,10*i+4,DownNum);
		  }else{g.drawString(rand,10*i+4,UpNum);}
	  }
     // g.drawString(rand,11*i+2,15);
     //g.drawString(rand,11*i+1,15);
  }
  
  session.setAttribute("admin_login_code",sRand);

  g.dispose();

  ImageIO.write(image, "JPEG", response.getOutputStream());
%>