package ArcSoLu.Excel;

import java.sql.Connection;



public interface IDB {
	
	Connection getConnection() ;
	//IDao getDao(DaoType type,Context cxt) throws DBConnectException,DBNoDefineDaoTypeException;
	void SetHost(String host);
	void SetDBName(String dbName);
	void SetUserName(String userName);
	void SetPassword(String pwd);
	void SetRetryTimes(int retry);
	boolean CreateDB();
	void Connect() ;
    void Disconnect() ;
    
    boolean StartTransAction() ;
    boolean CommitTransAction() ;
    boolean RollbackTransAction() ;
    boolean BackupTo();
        //IDao *CreateDao(DaoType type)=0;
	boolean IsValid();
}
