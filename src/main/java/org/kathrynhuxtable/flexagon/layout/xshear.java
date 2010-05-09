private void xshear(double shear, int width, int height) {

	for(int y = 0; y < height; y++) {
		double	skew = shear * (y + 0.5);
		int		skewi = (int) Math.floor(skew);
		double	skewf = Math.frac(skew);
		int		oleft = 0;

		for(int x = 0; x < width; x++) {
			pixel = raster[width - x + width * y];
			left  = pixmult(pixel, skewf);	// pixel = left + right

			pixel = pixel - left + oleft;	// pixel - left = right
			raster[width - x + skewi + width * y] = pixel;

			oleft = left;
		}

		raster[skewi, y] = oleft;
	}
}

int pixmult(int pix, double frac) {

	int r = (int) (((pix >> 16) & 0xFF) * frac);
	int g = (int) (((pix >>  8) & 0xFF) * frac);
	int b = (int) (((pix      ) & 0xFF) * frac);

	return (r << 16) | (g << 8) | b;
}
