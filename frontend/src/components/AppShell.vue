<template>
  <el-container class="app-shell">
    <el-header class="app-header">
      <div>
        <h1>员工管理系统</h1>
        <p>Employee Management</p>
      </div>
    </el-header>
    <el-main>
      <slot />
    </el-main>
  </el-container>
</template>

<style scoped>
.app-shell {
  min-height: 100vh;
  background: #f5f7fb;
}

.app-header {
  display: flex;
  align-items: center;
  height: 72px;
  border-bottom: 1px solid #e5e7eb;
  background: #ffffff;
}

h1 {
  margin: 0;
  color: #1f2937;
  font-size: 22px;
  font-weight: 600;
}

p {
  margin: 4px 0 0;
  color: #6b7280;
  font-size: 13px;
}
</style>
