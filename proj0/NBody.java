public class NBody {

    public static void main(String[] args) {
        /**
         * Collecting all the needed inputs
         */
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        /**
         * Drawing the background.
         */
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
//        StdDraw.picture(0,0,"images/starfield.jpg");
//        StdDraw.show();
//        /**
//         * Draw all the planets.
//         */
//        int num = planets.length;
//        for(int i = 0; i < num; i++) {
//            planets[i].draw();
//        }
        int num = planets.length;
        for(double time = 0; time < T; time += dt) {
            double[] xForces = new double[num];
            double[] yForces = new double[num];

            for(int i = 0; i < num; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.picture(0,0,"images/starfield.jpg");
            for(int i = 0; i < num; i++) {
                planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }

    }


    /**
     * Get the Radius of the universe
     * @param path
     * @return
     */
    public static double readRadius(String path) {
        In in = new In(path);
        in.readInt();
        return in.readDouble();
    }

    /**
     * Read the file and return the planets corresponding with the file.
     * @param path
     * @return
     */
    public static Planet[] readPlanets(String path) {
        In in = new In(path);
        int num_planet = in.readInt();
        in.readDouble();

        Planet[] planets = new Planet[num_planet];
        for(int i = 0; i < num_planet; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xxPos,yyPos,xxVel,yyVel,mass, img);
        }
        return planets;
    }


}
