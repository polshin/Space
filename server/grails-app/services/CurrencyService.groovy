import grails.plugins.rest.client.RestBuilder
import groovy.transform.*

import java.text.SimpleDateFormat

class CurrencyService {
    public static final String CBR_URL = "https://www.cbr.ru/currency_base/daily.aspx?date_req="
    boolean transactional = true
    def rest = new RestBuilder()


    private static List<Date> getDatesBetweenUsing(
            Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }

    def getByMonthMaps() {
        Date current = new Date();
        Date monthAgo = new Date(current.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(monthAgo);
        c.add(Calendar.MONTH, -1);
        monthAgo = c.getTime();
        List<Date> dates = getDatesBetweenUsing(monthAgo,current)
        dates.add(current)
        ArrayList<Map> monthData = new ArrayList<>();
        SimpleDateFormat frmt = new SimpleDateFormat("dd.MM.yyyy");
        for (Date day:dates) {
            String dateForRetrieve = frmt.format(day);
            Map currencies = getCurrenciesByDate(dateForRetrieve);
            Map forSend = new HashMap();
            forSend.put("USD",currencies.get("USD") );
            forSend.put("EUR",currencies.get("EUR"));
            forSend.put("DATE",dateForRetrieve);
            monthData.add(forSend)
        }
        return monthData
        //return (resp =~ /USD.*?<td>(.*?)<\/td>.*?<td>(.*?)<\/td>.*?<td>(.*?)<\/td>/)
    }
    @Memoized
    def getByMonthFromNow() {
        Date current = new Date();
        Date monthAgo = new Date(current.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(monthAgo);
        c.add(Calendar.MONTH, -1);
        monthAgo = c.getTime();
        List<Date> dates = getDatesBetweenUsing(monthAgo,current)
        dates.add(current)
        ArrayList<String[]> monthData = new ArrayList<>();
        SimpleDateFormat frmt = new SimpleDateFormat("dd.MM.yyyy");
        for (Date day:dates) {
            String dateForRetrieve = frmt.format(day);
            Map currencies = getCurrenciesByDate(dateForRetrieve);
            monthData.add([currencies.get("USD"),currencies.get("EUR"),dateForRetrieve])
        }
        return monthData
    }

    @Memoized
    def getUsdAndEur(String dateINDDMMYYYY) {
        Map currencies = getCurrenciesByDate(dateINDDMMYYYY);
        return [currencies.get("USD"),currencies.get("EUR"),dateINDDMMYYYY]
    }

    @Memoized
    def getCurrenciesByDate(String dateINDDMMYYYY) {
        List array = new ArrayList();
        Map<String,String> map = new HashMap<>();
        def resp = rest.get(CBR_URL + dateINDDMMYYYY).getText();
        def res = (resp =~ /<td>(.*?)<\\/td>/)
        res.each { array.push(it[1]) }
        def res2 = array.collate(5)
        for (ArrayList lst : res2) {
            map.put(lst.get(1),lst.get(4).toString().replace(",","."));
        }
        return map;
    }

}