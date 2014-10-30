package util;

import play.Logger;
import play.libs.F.Promise;
import play.mvc.*;

public class LogAction extends Action.Simple {
	@Override
	public Promise<Result> call(Http.Context ctx) throws Throwable {
		Logger.info("Вызов метода " + ctx);
		return delegate.call(ctx);
	}
}