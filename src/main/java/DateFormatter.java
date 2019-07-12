import java.time.LocalDate;
import java.time.Period;

public class DateFormatter {

	String date;

	public DateFormatter(String date) {
		this.date = date;
	}

	public int returnAgeFormStringDate(){


		int Years = Integer.parseInt(date.split("/")[2]);
		int months = Integer.parseInt(date.split("/")[1]);
		int days = Integer.parseInt(date.split("/")[0]);

		LocalDate l = LocalDate.of(Years, months, days); //specify year, month, date directly
		LocalDate now = LocalDate.now();
		Period diff = Period.between(l, now);

		return diff.getYears();
	}
}
