import grails.testing.mixin.integration.Integration
import grails.transaction.Rollback
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@SuppressWarnings('MethodName')
@Rollback
@Integration
class CurrencyRetrieverSpec  extends Specification  {
    @Autowired CurrencyService service
    void testRegexp() {
        service.getCurrenciesByDate("10.02.2018")
    }
}
