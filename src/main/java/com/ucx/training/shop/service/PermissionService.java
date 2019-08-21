package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Permission;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PermissionService extends BaseService<Permission, Integer> {
}
