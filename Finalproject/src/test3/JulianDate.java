package test3;

public class JulianDate { 
	private int year, mon, day;
	private int julianDate;
	
	public JulianDate(int year, int mon, int day) {
		this.year = year; this.mon = mon; this.day = day;
		year+=8000;
		if(mon<3) { year--; mon+=12; }
		julianDate = ((year*365) + (year/4) - (year/100) + (year/400) -1200820 + (mon*153+3)/5 - 92 + day - 1) - 2451544;
	}   // (2000 Jan.1) <----> JD(2451545)
	
	public int getJulianDate(){
		return julianDate;
	}
	public int getYear() {
		 int l = julianDate + 2451544 + 68569;
		 int N = ( 4 * l ) / 146097;
		 l = l - ( 146097 *N + 3 ) / 4;
		 int I = ( 4000 * ( l + 1 ) ) / 1461001;
		 l = l - ( 1461 * I ) / 4 + 31;
		 int J = ( 80 * l) / 2447;
		 l = J / 11;
		 int Year = 100 * ( N - 49 ) + I + l;
		 return Year;
	}
	public int getMonth() {
		 int l = julianDate + 2451544 + 68569;
		 int N = ( 4 * l ) / 146097;
		 l = l - ( 146097 *N + 3 ) / 4;
		 int I = ( 4000 * ( l + 1 ) ) / 1461001;
		 l = l - ( 1461 * I ) / 4 + 31;
		 int J = ( 80 * l) / 2447;
		 l = J / 11;
		 int Month = J +2 - ( 12 *l );
		 return Month;
	}
	public int getDay() {
		 int l = julianDate + 2451544 + 68569;
		 int N = ( 4 * l ) / 146097;
		 l = l - ( 146097 *N + 3 ) / 4;
		 int I = ( 4000 * ( l + 1 ) ) / 1461001;
		 l = l - ( 1461 * I ) / 4 + 31;
		 int J = ( 80 * l) / 2447;
		 int Day = l - ( 2447 * J ) / 80;
		 return Day;
	}
}


