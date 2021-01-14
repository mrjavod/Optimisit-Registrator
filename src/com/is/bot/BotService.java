package com.is.bot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.is.utils.ConnectionPool;
import com.is.utils.ISLogger;

public class BotService {

	public static ServiceResponse keyState(long chatId, String key) {

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ServiceResponse response = new ServiceResponse();
		SendMessage message = new SendMessage();
		message.setChatId(chatId);

		try {

			boolean isAccess = checkChatId(chatId);

			c = ConnectionPool.getConnection();
			ps = c.prepareStatement("SELECT * FROM users WHERE my_key=?");
			ps.setString(1, key);
			rs = ps.executeQuery();
			if (rs.next()) {
				long chat_id = rs.getLong("chat_id");
				String state = rs.getString("state");
				long id = rs.getLong("id");

				if (state.equals("INACTIVE")) {
					response.setStatus(0);
					message.setText("Ushbu kalit bloklangan ! \n");
				} else if (chat_id == chatId && isAccess) {
					response.setStatus(1);
					message.setText("Kalitni avvalroq ro'yhatdan o'tkazib bo'lgansiz. \n");
					registrUser(chatId, id);
				} else if (chat_id != chatId && isAccess) {
					response.setStatus(0);
					message.setText("Sizda 2-kalitni ishlatish huquqi mavjud emas ! \n");
				} else if (chat_id > 0 && state.equals("ADMIN")) {
					response.setStatus(0);
					message.setText("Bu kalitdan foydalanib bo'lingan, iltimos o'z kalitingizni kiriting !");
				} else if (chat_id == 0 && state.equals("ACTIVE") && !isAccess) {
					response.setStatus(1);
					message = registrUser(chatId, id);
				}

			} else {
				response.setStatus(0);
				message.setText("Bunday kalit mavjud emas !");
			}

			response.setMessage(message);

		} catch (Exception e) {
			ISLogger.getLogger().error(ConnectionPool.getPstr(e));
			message.setText("Iltimos kalit ni kiriting !");
			response.setStatus(0);
			response.setMessage(message);
		} finally {
			ConnectionPool.close(c);
			ConnectionPool.close(ps);
			ConnectionPool.close(rs);
		}

		return response;
	}

	private static SendMessage registrUser(long chatId, long id) {
		Connection c = null;
		PreparedStatement ps = null;
		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		try {
			c = ConnectionPool.getConnection();
			ps = c.prepareStatement("UPDATE users SET state=?, chat_id=?, updated_at=NOW() WHERE id=?");
			ps.setString(1, "ADMIN");
			ps.setLong(2, chatId);
			ps.setLong(3, id);
			ps.executeUpdate();
			c.commit();
			message.setText("Kalitingiz ro'yhatga olindi ! \n");
		} catch (Exception e) {
			ISLogger.getLogger().error(ConnectionPool.getPstr(e));
		} finally {
			ConnectionPool.close(c);
			ConnectionPool.close(ps);
		}
		return message;
	}

	private static boolean checkChatId(long chat_id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isAccess = false;
		try {
			c = ConnectionPool.getConnection();
			ps = c.prepareStatement("SELECT * FROM users WHERE chat_id=?");
			ps.setLong(1, chat_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				isAccess = true;
			}
		} catch (Exception e) {
			ISLogger.getLogger().error(ConnectionPool.getPstr(e));
		} finally {
			ConnectionPool.close(c);
			ConnectionPool.close(ps);
			ConnectionPool.close(rs);
		}
		return isAccess;
	}

	public static boolean isAccess(long chat_id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isAccess = false;
		try {
			c = ConnectionPool.getConnection();
			ps = c.prepareStatement("SELECT * FROM users WHERE chat_id=? and state=?");
			ps.setLong(1, chat_id);
			ps.setString(2, "ADMIN");
			rs = ps.executeQuery();
			if (rs.next()) {
				isAccess = true;
			}
		} catch (Exception e) {
			ISLogger.getLogger().error(ConnectionPool.getPstr(e));
		} finally {
			ConnectionPool.close(c);
			ConnectionPool.close(ps);
			ConnectionPool.close(rs);
		}
		return isAccess;
	}

	public static String generator(String level_code) {

		int id = new Random().nextInt((1000000) + 1);
		String mkey = "OPTIMIST" + level_code + "ABC" + "000" + id;

		boolean isStudentCreated = createStudent(mkey, id, level_code);
		if (isStudentCreated) {
			return mkey;
		}

		return null;
	}

	private static boolean createStudent(String mkey, int id, String level) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean isCreated = false;
		Long student_id = null;
		try {
			String generatedColumns[] = { "id" };

			c = ConnectionPool.getConnection();
			ps = c.prepareStatement(
					"INSERT INTO students(firstname,secondname,lastname,level,state,created_at,updated_at) VALUES (?,?,?,?,?,NOW(),NOW())",
					generatedColumns);
			ps.setString(1, "Student-" + id);
			ps.setString(2, "Student-" + id);
			ps.setString(3, "Student-" + id);
			ps.setString(4, level);
			ps.setString(5, "ACTIVE");
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				student_id = rs.getLong(1);
			}

			isCreated = createKey(c, mkey, student_id);
			if (isCreated) {
				c.commit();
			} else {
				c.rollback();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(c);
			ConnectionPool.close(ps);
		}
		return isCreated;
	}

	private static boolean createKey(Connection c, String mkey, Long student_id) {
		PreparedStatement ps = null;
		boolean isCreated = false;
		try {
			ps = c.prepareStatement(
					"INSERT INTO my_keys(my_key,student_id,state,chat_id,created_at,updated_at) VALUES (?,?,?,0,NOW(),NOW())");
			ps.setString(1, mkey);
			ps.setLong(2, student_id);
			ps.setString(3, "ACTIVE");
			ps.executeUpdate();
			c.commit();
			isCreated = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(ps);
		}
		return isCreated;
	}

}
