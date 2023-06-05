package DM1;

/**
 * 测试类
 */
public class ChainPatternDemo {

   /**
    * 组装责任链
    * @return
    */
   private static AbstractLogger getChainOfLoggers(){
      //创建日志器,并指定每个日志器的能处理级别
      AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);  //3
      AbstractLogger debugLogger = new FileLogger(AbstractLogger.DEBUG);   //2
      AbstractLogger infoLogger = new ConsoleLogger(AbstractLogger.INFO);  //1
      //组装职责链
      // error 3> debug 2> info 1
      errorLogger.setNextLogger(debugLogger);
      debugLogger.setNextLogger(infoLogger);
      return errorLogger;   //3
   }
 
   public static void main(String[] args) {
      //获取日志职责链
      AbstractLogger loggerChain = getChainOfLoggers();

      //                                  1
     // loggerChain.logMessage(AbstractLogger.INFO, "This is an information.");
 
     loggerChain.logMessage(AbstractLogger.DEBUG, "This is a debug level information.");
 
    //  loggerChain.logMessage(AbstractLogger.ERROR, "This is an error information.");
   }
}