require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name             = 'MetamapCapacitorPlugin'
  s.version          = package['version']
  s.summary          = package['description']
  s.license          = package['license']
  s.homepage         = package['repository']['url']
  s.author           = { 'Avo Sukiasyan' => 'avetik.sukiasyan@metamap.com' }
  s.source           = { :path => '.' }
  s.ios.deployment_target = '13.0'
  s.swift_version         = '5.1'
  s.static_framework      = true

  s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'

  # Dependencies
  s.dependency 'Capacitor'
  s.dependency 'MetaMapSDK', '3.23.0'
end