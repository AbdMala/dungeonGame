package sopra.controller;

import sopra.utils.Utils;


public class ServerError extends IllegalStateException {

  private static final long serialVersionUID = 4579405871197048L;

  public ServerError(final String format, final Object... args) {
    super(Utils.substitute(format, args));
  }

  public ServerError() {
    super();
  }

  public ServerError(final Exception e) {
    super(e);
  }
}
