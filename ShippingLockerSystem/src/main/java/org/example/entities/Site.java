package org.example.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Site {
    private final List<Locker> lockers;
    private final Map<String, AccessToken> accessTokenMap;
    private final Random random;

    public Site(List<Locker> lockers) {
        this.lockers = lockers;
        accessTokenMap = new HashMap<>();
        random = new Random();
    }

    public String assignPackage(Package aPackage) {
        if(aPackage == null) {
            throw new IllegalArgumentException("Null package");
        }

        Size size = getSizeForPackage(aPackage.getPackageSize());
        Locker locker = getAvailableLocker(size);
        if(locker == null) {
            throw new RuntimeException("Locker is not available for given package size");
        }
        locker.assignPackage(aPackage);
        AccessToken accessToken = generateAccessToken(locker);
        accessTokenMap.put(accessToken.getCode(), accessToken);
        return accessToken.getCode();
    }

    public void pickup(String tokenCode) {
        if(tokenCode == null || tokenCode.isEmpty()) {
            throw new RuntimeException("Invalid access token code");
        }

        AccessToken accessToken = accessTokenMap.get(tokenCode);
        if(accessToken == null) {
            throw new RuntimeException("Invalid access token code");
        }

        if(accessToken.isExpired()) {
            throw new RuntimeException("Access token has expired, contact customer support");
        }

        Locker locker = accessToken.getLocker();
        locker.markFree();
        accessTokenMap.remove(accessToken.getCode());
    }


    private AccessToken generateAccessToken(Locker locker) {
        String code = String.format("%06d", random.nextInt(1_000_000));
        Timestamp expiration = Timestamp.from(Instant.now().plus(7, ChronoUnit.DAYS));
        return new AccessToken(code, expiration, locker);
    }

    private Locker getAvailableLocker(Size size) {
        for(Locker locker: lockers) {
            if(!locker.isOccupied() && locker.getSize() == size) {
               return locker;
            }
        }
        return null;
    }

    private Size getSizeForPackage(PackageSize packageSize) {
        for(Size size: Size.values()) {
            if(size.getHeight().compareTo(packageSize.getHeight()) >= 0 &&
                    size.getWidth().compareTo(packageSize.getWidth()) >= 0 &&
                    size.getDepth().compareTo(packageSize.getDepth()) >= 0
            ) {
                return size;
            }
        }
        throw new RuntimeException("Package size is not available");
    }
}
