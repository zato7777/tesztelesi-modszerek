package szakdolgozat.ujkripto.server;

public class UjkriptoServerFactory implements UjkriptoServerFactoryInterface{
    public UjkriptoServerInterface newUjkriptoServer() {
      return UjkriptoServer.getInstance();
    }
}
