import java.lang.Math;
public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public static double G = 6.67e-11;

	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p) {
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p) {

		return Math.sqrt((this.xxPos - p.xxPos)*(this.xxPos - p.xxPos) + (this.yyPos - p.yyPos)*(this.yyPos - p.yyPos));
	}

	public double calcForceExertedBy(Planet p) {
		double r = calcDistance(p);
		return Planet.G * this.mass * p.mass / (r * r);
	}

	public double calcForceExertedByX(Planet p) {
		return calcForceExertedBy(p) * (p.xxPos - this.xxPos) / calcDistance(p);
	}

	public double calcForceExertedByY(Planet p) {
		return calcForceExertedBy(p) * (p.yyPos - this.yyPos) / calcDistance(p);
	}

	public double calcNetForceExertedByX(Planet[] planets) {
		double netX = 0;
		int len = planets.length;
		for(int i = 0; i < len; i++) {
			Planet p = planets[i];
			if (!this.equals(p)) {
				netX += calcForceExertedByX(p);
			}
		}
		return netX;
	}

	public double calcNetForceExertedByY(Planet[] planets) {
		double netY = 0;
		int len = planets.length;
		for(int i = 0; i < len; i++) {
			Planet p = planets[i];
			if (!this.equals(p)) {
				netY += calcForceExertedByY(p);
			}
		}
		return netY;
	}

	public void update(double dt, double fX, double fY) {
		double a_x = fX/this.mass;
		double a_y = fY/this.mass;
		xxVel += a_x * dt;
		yyVel += a_y *dt;
		xxPos += xxVel * dt;
		yyPos += yyVel * dt;
	}

	public void draw() {
		StdDraw.picture(xxPos,yyPos,"images/" + imgFileName);
		StdDraw.show();
	}

}