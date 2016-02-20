package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Transaction;
import com.google.cloudsearch.v1.FieldValue.StringFormat;

public class DatastorePreparedStatement {
	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private static Transaction transaction = datastore.beginTransaction();
	public DatastorePreparedStatement() {
		datastore = DatastoreServiceFactory.getDatastoreService();
		transaction = datastore.beginTransaction();
	}
	public static boolean insertInto(Entity entity){
		datastore = DatastoreServiceFactory.getDatastoreService();
		transaction = datastore.beginTransaction();
			datastore.put(entity);
			try {
				transaction.commit();
			} catch (Exception e) {
				return false;
			}finally{
				if(transaction.isActive()){
					transaction.rollback();
				}
			}
		return true;
	}
	public static Entity selectWhere(String tableName, String key, String value){
		datastore = DatastoreServiceFactory.getDatastoreService();
		transaction = datastore.beginTransaction();
		Query gaeQuery = new Query(tableName);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		if(key == "ID"){
			
			for (Entity entity : pq.asIterable()){
				
				if(String.format("%s", entity.getKey().getId()).equals(value)){
					return entity;
				}
			}
		}else{
			for (Entity entity : pq.asIterable()){
				if(entity.getProperty(key).toString().equals(value)){
					return entity;
				}
			}
		}
		return null;
	}
	public static boolean updateWhere(String tableName, String key, String value, Map<String, String>newEntity){
		datastore = DatastoreServiceFactory.getDatastoreService();
		transaction = datastore.beginTransaction();
		Query gaeQuery = new Query(tableName);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		if(key.equals("ID")){
			for (Entity entity : pq.asIterable()){
				if(String.format("%s", entity.getKey().getId()).equals(value)){
					
					for(String newKey : newEntity.keySet()){
						if(newEntity.containsKey(newKey)){
							String newValue = newEntity.get(newKey);
							entity.setProperty(newKey, newValue);
							System.out.println(entity.toString());
						}else{
							return false;
						}
					}
					datastore.put(entity);
					try {
						transaction.commit();
					} catch (Exception e) {
						return false;
					}finally{
						if(transaction.isActive()){
							transaction.rollback();
						}
					}
					break;
				}
			}
		}else{
			for (Entity entity : pq.asIterable()){
				if(entity.getProperty(key).toString().equals(value)){
					
					for(String newKey : newEntity.keySet()){
						if(newEntity.containsKey(newKey)){
							String newValue = newEntity.get(newKey);
							entity.setProperty(newKey, newValue);
						}else{
							return false;
						}
					}
					datastore.put(entity);
					try {
						transaction.commit();
					} catch (Exception e) {
						// TODO: handle exception
					}finally{
						if(transaction.isActive()){
							transaction.rollback();
						}
					}
					break;
				}
			}
		}
		return true;
	}
	public static boolean appendWhere(String tableName, String key, String value, String newKey, String object){
		datastore = DatastoreServiceFactory.getDatastoreService();
		transaction = datastore.beginTransaction();
		Query gaeQuery = new Query(tableName);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		if(key.equals("ID")){
			for (Entity entity : pq.asIterable()){
				if(String.format("%s", entity.getKey().getId()).equals(value)){
					
					String newValue = entity.getProperty(newKey).toString()+object;
					entity.setProperty(newKey, newValue);
					datastore.put(entity);
					try {
						transaction.commit();
					} catch (Exception e) {
						// TODO: handle exception
					}finally{
						if(transaction.isActive()){
							transaction.rollback();
						}
					}
					
					break;
				}
			}
		}else{
			for (Entity entity : pq.asIterable()){
				if(entity.getProperty(key).toString().equals(value)){
					
					String newValue = entity.getProperty(newKey).toString()+object;
					entity.setProperty(newKey, newValue);
					datastore.put(entity);
					try {
						transaction.commit();
					} catch (Exception e) {
						// TODO: handle exception
					}finally{
						if(transaction.isActive()){
							transaction.rollback();
						}
						
					}
					break;
				}
			}
		}
		return true;
	}
	public static boolean removeWhere(String tableName, String key, String value, String newKey, String object){
		datastore = DatastoreServiceFactory.getDatastoreService();
		transaction = datastore.beginTransaction();
		Query gaeQuery = new Query(tableName);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		if(key.equals("ID")){
			for (Entity entity : pq.asIterable()){
				if(String.format("%s", entity.getKey().getId()).equals(value)){
					
					String newValue = entity.getProperty(newKey).toString();
					newValue = newValue.replaceFirst(object, "");
					entity.setProperty(newKey, newValue);
					datastore.put(entity);
					try {
						transaction.commit();
					} catch (Exception e) {
						return false;
					}finally{
						if(transaction.isActive()){
							transaction.rollback();
						}
					}
					break;
				}
			}
		}else{
			for (Entity entity : pq.asIterable()){
				if(entity.getProperty(key).toString().equals(value)){
					
					String newValue = entity.getProperty(newKey).toString();
					newValue = newValue.replaceFirst(object, "");
					entity.setProperty(newKey, newValue);
					datastore.put(entity);
					try {
						transaction.commit();
					} catch (Exception e) {
						return false;
					}finally{
						if(transaction.isActive()){
							transaction.rollback();
						}
					}
					break;
				}
			}
		}
		return true;
	}
	public static boolean deleteWhere(String tableName, String key, String value){
		datastore = DatastoreServiceFactory.getDatastoreService();
		transaction = datastore.beginTransaction();
		Query gaeQuery = new Query(tableName);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		if(key == "ID"){
			for (Entity entity : pq.asIterable()){
				if(String.format("%s", entity.getKey().getId()).equals(value)){
					
					datastore.delete(entity.getKey());
					try {
						transaction.commit();
					} catch (Exception e) {
						// TODO: handle exception
					}finally{
						if(transaction.isActive()){
							transaction.rollback();
						}
					}
					break;
				}
			}
		}else{
			for (Entity entity : pq.asIterable()){
				if(entity.getProperty(key).toString().equals(value)){
					
					datastore.delete(entity.getKey());
					try {
						transaction.commit();
					} catch (Exception e) {
						// TODO: handle exception
					}finally{
						if(transaction.isActive()){
							transaction.rollback();
						}
					}
					break;
				}
			}
		}
		return true;
	}
	public static int increment(String key, int value){
		datastore = DatastoreServiceFactory.getDatastoreService();
		transaction = datastore.beginTransaction();
		Query gaeQuery = new Query("Statistics");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		Entity entity = pq.asList(FetchOptions.Builder.withDefaults()).get(0);
		int newValue = Integer.parseInt(entity.getProperty(key).toString())+value;
		entity.setProperty(key, newValue);
		datastore.put(entity);
		try {
			transaction.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(transaction.isActive()){
				transaction.rollback();
			}
		}
		return newValue;
	}
	public static List<Entity> retrieveAll(String tableName){
		datastore = DatastoreServiceFactory.getDatastoreService();
		transaction = datastore.beginTransaction();
		Query gaeQuery = new Query(tableName);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> entitties = pq.asList(FetchOptions.Builder.withDefaults());
		return entitties;
	}
	public static List<Entity> retrieveAllWhere(String tableName, String key, String value){
		datastore = DatastoreServiceFactory.getDatastoreService();
		transaction = datastore.beginTransaction();
		Query gaeQuery = new Query(tableName);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> entitties = new ArrayList<Entity>();
		for (Entity entity : pq.asIterable()) {
			if(entity.getProperty(key).toString().equals(value)){
				entitties.add(entity);
			}
		}
		
		return entitties;
	}
	public static String getBlobType(String tableName, String key, String value){
		datastore = DatastoreServiceFactory.getDatastoreService();
		transaction = datastore.beginTransaction();
		Query gaeQuery = new Query(tableName);
		PreparedQuery pq = datastore.prepare(gaeQuery);
			
			for (Entity entity : pq.asIterable()){
				
				if(String.format("%s", entity.getKey().getName()).equals(value)){
					return entity.getProperty("content_type").toString();
				}
			}
		return null;
	}
} 
