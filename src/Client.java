import com.Utils.TopHandler;
import com.View.IndexFrame;
import com.View.LoginPanel;
import com.View.MineField;
import com.View.OperatePanel;
import com.conection.Connection;
import com.event.Core;
import com.event.MyMouseListener;
import com.test.TestMockRequest;

public class Client {
    // 客户端
    public static void main(String[] argv){

        // 配置参数
        System.setProperty("Dfile.encoding","UTF-8");
        LoginPanel loginPanel = new LoginPanel();// 登陆面板
        MyMouseListener myMouseListener = new MyMouseListener(); //雷区鼠标监听器
        MineField ownMineField = new MineField(); // 我方雷区
        // 设置监听器
        ownMineField.setMouseListener(myMouseListener.getListener());
        OperatePanel operatePanel = new OperatePanel(); // 操作面板
        MineField opponentMineField = new MineField(); // 对方雷区
        Connection connection = new Connection(); // 连接模块
        IndexFrame indexFrame = new IndexFrame( ownMineField, operatePanel, opponentMineField ); // 首页
        Core core = new Core(); // 游戏核心处理器
        TopHandler topHandler = new TopHandler(core,indexFrame,loginPanel); // 顶层处理者
        topHandler.setConnection(connection); // 设置连接器
        // 设置责任链
        // loginPanel -> myMouseListener -> core -> operatePanel
        // -> ownMineField -> connection -> opponentMineField -> topHandler
        // 模拟器
//        TestMockRequest testMockRequest = new TestMockRequest();
//        testMockRequest.setSuccessor(myMouseListener);

        loginPanel.setSuccessor(myMouseListener);
        myMouseListener.setSuccessor(core);
        core.setSuccessor(operatePanel);
        operatePanel.setSuccessor(ownMineField);
        ownMineField.setSuccessor(connection);
        connection.setSuccessor(opponentMineField);
        opponentMineField.setSuccessor(topHandler);

    }
}
