@Echo off
cls
echo "Bulding!!!!..........................."
call mvn -B -q package
echo "Copying!!!!..........................."
copy /b /y  T:\Workspaces\Kepler\projetos\cloudsystems\System\target\System.ear T:\servers\jboss\7.1.1\standalone\deployments
echo "Done!!!!!!!..........................."
