import ACCESS_ENUM from '@/access/ACCESS_ENUM.ts'
import type { LoginUserVO } from '../../generated'

/**
 * 判断用户是否具有某权限
 * @param loginUser 登录的用户
 * @param needAccess 需要的权限
 * @return 是否有权限
 */
const checkAccess = (loginUser:LoginUserVO, needAccess:string) => {
  // 登录用户的权限
  const loginUserAccess = loginUser?.userRole ?? ACCESS_ENUM.NOT_LOGIN
  // 不要任何权限
  if (needAccess === ACCESS_ENUM.NOT_LOGIN) {
    return true
  }
  // 需要登录
  if (needAccess === ACCESS_ENUM.USER) {
    // 未登录，拒绝
    if (loginUserAccess === ACCESS_ENUM.NOT_LOGIN) {
      return false
    }
  }
  // 管理员权限
  if (needAccess === ACCESS_ENUM.ADMIN) {
    if (loginUserAccess !== ACCESS_ENUM.ADMIN) {
      return false
    }
  }
  return true
}

export default checkAccess
