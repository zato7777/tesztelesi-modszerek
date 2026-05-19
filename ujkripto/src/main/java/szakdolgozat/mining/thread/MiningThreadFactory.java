package szakdolgozat.mining.thread;

public class MiningThreadFactory implements MiningThreadFactoryInterface{
  public MiningThreadInterface newMiningThread() {
    return new MiningThread();
  }
}
