import com.Utils.TopHandler;
import com.View.IndexFrame;
import com.View.LoginPanel;
import com.View.MineField;
import com.View.OperatePanel;
import com.conection.Connection;
import com.event.Core;
import com.event.MyMouseListener;

public class Client {
    // 客户端
    public static void main(String[] argv){

        LoginPanel loginPanel = new LoginPanel();// 登陆面板
        MyMouseListener myMouseListener = new MyMouseListener(); //雷区鼠标监听器
        MineField ownMineField = new MineField(); // 我方雷区
        // 设置监听器
        ownMineField.setMouseListener(myMouseListener.getListener());
        OperatePanel operatePanel = new OperatePanel(); // 操作面板
        MineField opponentMineField = new MineField(); // 对方雷区
        Connection connection = new Connection(); // 连接模块
        IndexFrame indexFrame = new IndexFrame(
                ownMineField.getMineFieldJpanel(),
                operatePanel.getContent(),
                opponentMineField.getMineFieldJpanel()
        ); // 首页
        Core core = new Core(); // 游戏核心处理器
        TopHandler topHandler = new TopHandler(); // 顶层处理者
        topHandler.setLoginPanel(loginPanel);
        topHandler.setOperatePanel(operatePanel);
        topHandler.setIndexFrame(indexFrame);

        // 设置责任链
        loginPanel.setSuccessor(operatePanel);
        operatePanel.setSuccessor(myMouseListener);
        myMouseListener.setSuccessor(core);
        core.setSuccessor(ownMineField);
        ownMineField.setSuccessor(connection);
        connection.setSuccessor(opponentMineField);
        opponentMineField.setSuccessor(topHandler);

    }
}
