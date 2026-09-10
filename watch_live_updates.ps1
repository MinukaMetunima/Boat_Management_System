# ====================================================================
# SAIL LANKA BOAT SAFARI - REAL-TIME LIVE DATABASE TABLES MONITOR
# Watches TABLE_LOGIN_DETAILS.txt, TABLE_CUSTOMER_DETAILS.txt,
# and all TABLE_*.txt files live
# ====================================================================

Clear-Host
Write-Host "========================================================================" -ForegroundColor Cyan
Write-Host "   SAIL LANKA BOAT SAFARI - REAL-TIME LIVE DATABASE TABLES MONITOR" -ForegroundColor Yellow
Write-Host "   Connected to MySQL 8.0: boat_safari_db @ localhost:3306" -ForegroundColor Green
Write-Host "   Monitoring all real-time table exports (TABLE_*.txt)" -ForegroundColor White
Write-Host "   Press Ctrl+C to Stop Monitoring" -ForegroundColor Gray
Write-Host "========================================================================" -ForegroundColor Cyan
Write-Host ""

$tables = Get-ChildItem (Join-Path $PSScriptRoot "TABLE_*.txt")
Write-Host "Discovered $($tables.Count) Live Database Table Files:" -ForegroundColor Yellow
foreach ($t in $tables) {
    Write-Host "  - $($t.Name)" -ForegroundColor Gray
}
Write-Host ""

$loginDetailsPath = Join-Path $PSScriptRoot "TABLE_LOGIN_DETAILS.txt"
$customerDetailsPath = Join-Path $PSScriptRoot "TABLE_CUSTOMER_DETAILS.txt"

if (Test-Path $loginDetailsPath) {
    Write-Host "--- RECENT LOGIN & AUTHENTICATION DETAILS ---" -ForegroundColor Yellow
    Get-Content $loginDetailsPath -Head 20
    Write-Host ""
}

if (Test-Path $customerDetailsPath) {
    Write-Host "--- RECENT REGISTERED CUSTOMER DETAILS ---" -ForegroundColor Cyan
    Get-Content $customerDetailsPath -Head 20
    Write-Host ""
}

Write-Host ">>> NOW MONITORING IN REAL TIME FOR TABLE UPDATES (Press Ctrl+C to quit)..." -ForegroundColor Green

# Track last modified timestamps
$lastModTimes = @{}
foreach ($t in $tables) {
    $lastModTimes[$t.FullName] = $t.LastWriteTime
}

while ($true) {
    Start-Sleep -Milliseconds 1000
    $currentTables = Get-ChildItem (Join-Path $PSScriptRoot "TABLE_*.txt")
    foreach ($t in $currentTables) {
        $lastTime = $lastModTimes[$t.FullName]
        if ($null -eq $lastTime -or $t.LastWriteTime -gt $lastTime) {
            $lastModTimes[$t.FullName] = $t.LastWriteTime
            $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
            Write-Host "[$timestamp] [LIVE TABLE REFRESH] $($t.Name) was updated live! (Size: $($t.Length) bytes)" -ForegroundColor Green
        }
    }
}
