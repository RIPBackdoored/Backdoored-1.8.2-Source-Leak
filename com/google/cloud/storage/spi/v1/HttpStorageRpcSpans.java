package com.google.cloud.storage.spi.v1;

import com.google.common.collect.*;
import io.opencensus.trace.*;
import java.util.*;

class HttpStorageRpcSpans
{
    static final String SPAN_NAME_CLIENT_PREFIX = "Sent";
    static final String SPAN_NAME_CREATE_BUCKET;
    static final String SPAN_NAME_CREATE_OBJECT;
    static final String SPAN_NAME_LIST_BUCKETS;
    static final String SPAN_NAME_LIST_OBJECTS;
    static final String SPAN_NAME_GET_BUCKET;
    static final String SPAN_NAME_GET_OBJECT;
    static final String SPAN_NAME_PATCH_BUCKET;
    static final String SPAN_NAME_PATCH_OBJECT;
    static final String SPAN_NAME_DELETE_BUCKET;
    static final String SPAN_NAME_DELETE_OBJECT;
    static final String SPAN_NAME_CREATE_BATCH;
    static final String SPAN_NAME_COMPOSE;
    static final String SPAN_NAME_LOAD;
    static final String SPAN_NAME_READ;
    static final String SPAN_NAME_OPEN;
    static final String SPAN_NAME_WRITE;
    static final String SPAN_NAME_OPEN_REWRITE;
    static final String SPAN_NAME_CONTINUE_REWRITE;
    static final String SPAN_NAME_GET_BUCKET_ACL;
    static final String SPAN_NAME_DELETE_BUCKET_ACL;
    static final String SPAN_NAME_CREATE_BUCKET_ACL;
    static final String SPAN_NAME_PATCH_BUCKET_ACL;
    static final String SPAN_NAME_LIST_BUCKET_ACLS;
    static final String SPAN_NAME_GET_OBJECT_DEFAULT_ACL;
    static final String SPAN_NAME_DELETE_OBJECT_DEFAULT_ACL;
    static final String SPAN_NAME_CREATE_OBJECT_DEFAULT_ACL;
    static final String SPAN_NAME_PATCH_OBJECT_DEFAULT_ACL;
    static final String SPAN_NAME_LIST_OBJECT_DEFAULT_ACLS;
    static final String SPAN_NAME_GET_OBJECT_ACL;
    static final String SPAN_NAME_DELETE_OBJECT_ACL;
    static final String SPAN_NAME_CREATE_OBJECT_ACL;
    static final String SPAN_NAME_PATCH_OBJECT_ACL;
    static final String SPAN_NAME_LIST_OBJECT_ACLS;
    static final String SPAN_NAME_CREATE_HMAC_KEY;
    static final String SPAN_NAME_GET_HMAC_KEY;
    static final String SPAN_NAME_DELETE_HMAC_KEY;
    static final String SPAN_NAME_LIST_HMAC_KEYS;
    static final String SPAN_NAME_UPDATE_HMAC_KEY;
    static final String SPAN_NAME_GET_BUCKET_IAM_POLICY;
    static final String SPAN_NAME_SET_BUCKET_IAM_POLICY;
    static final String SPAN_NAME_TEST_BUCKET_IAM_PERMISSIONS;
    static final String SPAN_NAME_DELETE_NOTIFICATION;
    static final String SPAN_NAME_LIST_NOTIFICATIONS;
    static final String SPAN_NAME_CREATE_NOTIFICATION;
    static final String SPAN_LOCK_RETENTION_POLICY;
    static final String SPAN_NAME_GET_SERVICE_ACCOUNT;
    static final String SPAN_NAME_BATCH_SUBMIT;
    static final ImmutableSet<String> ALL_SPAN_NAMES;
    
    static String getTraceSpanName(final String a1) {
        return String.format("%s.%s.%s", "Sent", HttpStorageRpc.class.getName(), a1);
    }
    
    static void registerAllSpanNamesForCollection() {
        Tracing.getExportComponent().getSampledSpanStore().registerSpanNamesForCollection((Collection)HttpStorageRpcSpans.ALL_SPAN_NAMES);
    }
    
    private HttpStorageRpcSpans() {
        super();
    }
    
    static {
        SPAN_NAME_CREATE_BUCKET = getTraceSpanName("create(Bucket,Map)");
        SPAN_NAME_CREATE_OBJECT = getTraceSpanName("create(StorageObject,InputStream,Map)");
        SPAN_NAME_LIST_BUCKETS = getTraceSpanName("list(Map)");
        SPAN_NAME_LIST_OBJECTS = getTraceSpanName("create(String,Map)");
        SPAN_NAME_GET_BUCKET = getTraceSpanName("get(Bucket,Map)");
        SPAN_NAME_GET_OBJECT = getTraceSpanName("get(StorageObject,Map)");
        SPAN_NAME_PATCH_BUCKET = getTraceSpanName("patch(Bucket,Map)");
        SPAN_NAME_PATCH_OBJECT = getTraceSpanName("patch(StorageObject,Map)");
        SPAN_NAME_DELETE_BUCKET = getTraceSpanName("delete(Bucket,Map)");
        SPAN_NAME_DELETE_OBJECT = getTraceSpanName("delete(StorageObject,Map)");
        SPAN_NAME_CREATE_BATCH = getTraceSpanName("createBatch()");
        SPAN_NAME_COMPOSE = getTraceSpanName("compose(Iterable,StorageObject,Map)");
        SPAN_NAME_LOAD = getTraceSpanName("load(StorageObject,Map");
        SPAN_NAME_READ = getTraceSpanName("read(StorageObject,Map,long,int)");
        SPAN_NAME_OPEN = getTraceSpanName("open(StorageObject,Map)");
        SPAN_NAME_WRITE = getTraceSpanName("write(String,byte[],int,long,int,boolean)");
        SPAN_NAME_OPEN_REWRITE = getTraceSpanName("openRewrite(RewriteRequest)");
        SPAN_NAME_CONTINUE_REWRITE = getTraceSpanName("continueRewrite(RewriteResponse)");
        SPAN_NAME_GET_BUCKET_ACL = getTraceSpanName("getAcl(String,String,Map)");
        SPAN_NAME_DELETE_BUCKET_ACL = getTraceSpanName("deleteAcl(String,String,Map)");
        SPAN_NAME_CREATE_BUCKET_ACL = getTraceSpanName("createAcl(BucketAccessControl,Map)");
        SPAN_NAME_PATCH_BUCKET_ACL = getTraceSpanName("patchAcl(BucketAccessControl,Map)");
        SPAN_NAME_LIST_BUCKET_ACLS = getTraceSpanName("listAcls(String,Map)");
        SPAN_NAME_GET_OBJECT_DEFAULT_ACL = getTraceSpanName("getDefaultAcl(String,String)");
        SPAN_NAME_DELETE_OBJECT_DEFAULT_ACL = getTraceSpanName("deleteDefaultAcl(String,String)");
        SPAN_NAME_CREATE_OBJECT_DEFAULT_ACL = getTraceSpanName("createDefaultAcl(ObjectAccessControl)");
        SPAN_NAME_PATCH_OBJECT_DEFAULT_ACL = getTraceSpanName("patchDefaultAcl(ObjectAccessControl)");
        SPAN_NAME_LIST_OBJECT_DEFAULT_ACLS = getTraceSpanName("listDefaultAcls(String)");
        SPAN_NAME_GET_OBJECT_ACL = getTraceSpanName("getAcl(String,String,Long,String)");
        SPAN_NAME_DELETE_OBJECT_ACL = getTraceSpanName("deleteAcl(String,String,Long,String)");
        SPAN_NAME_CREATE_OBJECT_ACL = getTraceSpanName("createAcl(ObjectAccessControl)");
        SPAN_NAME_PATCH_OBJECT_ACL = getTraceSpanName("patchAcl(ObjectAccessControl)");
        SPAN_NAME_LIST_OBJECT_ACLS = getTraceSpanName("listAcls(String,String,Long)");
        SPAN_NAME_CREATE_HMAC_KEY = getTraceSpanName("createHmacKey(String)");
        SPAN_NAME_GET_HMAC_KEY = getTraceSpanName("getHmacKey(String)");
        SPAN_NAME_DELETE_HMAC_KEY = getTraceSpanName("deleteHmacKey(String)");
        SPAN_NAME_LIST_HMAC_KEYS = getTraceSpanName("listHmacKeys(String,String,Long)");
        SPAN_NAME_UPDATE_HMAC_KEY = getTraceSpanName("updateHmacKey(HmacKeyMetadata)");
        SPAN_NAME_GET_BUCKET_IAM_POLICY = getTraceSpanName("getIamPolicy(String,Map)");
        SPAN_NAME_SET_BUCKET_IAM_POLICY = getTraceSpanName("setIamPolicy(String,Policy,Map)");
        SPAN_NAME_TEST_BUCKET_IAM_PERMISSIONS = getTraceSpanName("testIamPermissions(String,List,Map)");
        SPAN_NAME_DELETE_NOTIFICATION = getTraceSpanName("deleteNotification(String,String)");
        SPAN_NAME_LIST_NOTIFICATIONS = getTraceSpanName("listNotifications(String)");
        SPAN_NAME_CREATE_NOTIFICATION = getTraceSpanName("createNotification(String,Notification)");
        SPAN_LOCK_RETENTION_POLICY = getTraceSpanName("lockRetentionPolicy(String,Long)");
        SPAN_NAME_GET_SERVICE_ACCOUNT = getTraceSpanName("getServiceAccount(String)");
        SPAN_NAME_BATCH_SUBMIT = getTraceSpanName(RpcBatch.class.getName() + ".submit()");
        ALL_SPAN_NAMES = ImmutableSet.of((Object)HttpStorageRpcSpans.SPAN_NAME_CREATE_BUCKET, (Object)HttpStorageRpcSpans.SPAN_NAME_CREATE_OBJECT, (Object)HttpStorageRpcSpans.SPAN_NAME_LIST_BUCKETS, (Object)HttpStorageRpcSpans.SPAN_NAME_LIST_OBJECTS, (Object)HttpStorageRpcSpans.SPAN_NAME_GET_BUCKET, (Object)HttpStorageRpcSpans.SPAN_NAME_GET_OBJECT, (Object[])new String[] { HttpStorageRpcSpans.SPAN_NAME_PATCH_BUCKET, HttpStorageRpcSpans.SPAN_NAME_PATCH_OBJECT, HttpStorageRpcSpans.SPAN_NAME_DELETE_BUCKET, HttpStorageRpcSpans.SPAN_NAME_DELETE_OBJECT, HttpStorageRpcSpans.SPAN_NAME_CREATE_BATCH, HttpStorageRpcSpans.SPAN_NAME_COMPOSE, HttpStorageRpcSpans.SPAN_NAME_LOAD, HttpStorageRpcSpans.SPAN_NAME_READ, HttpStorageRpcSpans.SPAN_NAME_OPEN, HttpStorageRpcSpans.SPAN_NAME_WRITE, HttpStorageRpcSpans.SPAN_NAME_OPEN_REWRITE, HttpStorageRpcSpans.SPAN_NAME_CONTINUE_REWRITE, HttpStorageRpcSpans.SPAN_NAME_GET_BUCKET_ACL, HttpStorageRpcSpans.SPAN_NAME_DELETE_BUCKET_ACL, HttpStorageRpcSpans.SPAN_NAME_CREATE_BUCKET_ACL, HttpStorageRpcSpans.SPAN_NAME_PATCH_BUCKET_ACL, HttpStorageRpcSpans.SPAN_NAME_LIST_BUCKET_ACLS, HttpStorageRpcSpans.SPAN_NAME_GET_OBJECT_DEFAULT_ACL, HttpStorageRpcSpans.SPAN_NAME_DELETE_OBJECT_DEFAULT_ACL, HttpStorageRpcSpans.SPAN_NAME_CREATE_OBJECT_DEFAULT_ACL, HttpStorageRpcSpans.SPAN_NAME_PATCH_OBJECT_DEFAULT_ACL, HttpStorageRpcSpans.SPAN_NAME_LIST_OBJECT_DEFAULT_ACLS, HttpStorageRpcSpans.SPAN_NAME_GET_OBJECT_ACL, HttpStorageRpcSpans.SPAN_NAME_DELETE_OBJECT_ACL, HttpStorageRpcSpans.SPAN_NAME_CREATE_OBJECT_ACL, HttpStorageRpcSpans.SPAN_NAME_PATCH_OBJECT_ACL, HttpStorageRpcSpans.SPAN_NAME_LIST_OBJECT_ACLS, HttpStorageRpcSpans.SPAN_NAME_GET_BUCKET_IAM_POLICY, HttpStorageRpcSpans.SPAN_NAME_SET_BUCKET_IAM_POLICY, HttpStorageRpcSpans.SPAN_NAME_TEST_BUCKET_IAM_PERMISSIONS, HttpStorageRpcSpans.SPAN_NAME_DELETE_NOTIFICATION, HttpStorageRpcSpans.SPAN_NAME_LIST_NOTIFICATIONS, HttpStorageRpcSpans.SPAN_NAME_CREATE_NOTIFICATION, HttpStorageRpcSpans.SPAN_NAME_GET_SERVICE_ACCOUNT, HttpStorageRpcSpans.SPAN_LOCK_RETENTION_POLICY, HttpStorageRpcSpans.SPAN_NAME_BATCH_SUBMIT });
    }
}
