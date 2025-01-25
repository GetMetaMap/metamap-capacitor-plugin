require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name = 'MetamapCapacitorPlugin'
  s.version = package['version']
  s.summary = package['description']
  s.license = package['license']
  s.homepage = package['repository']['url']
  s.author = package['author']
  s.source = { :path => '.' } # Local path for the plugin's source
  s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}' # Source files for Capacitor plugin

  # Minimum iOS version for compatibility
  s.ios.deployment_target = '13.0'

  # Capacitor dependency
  s.dependency 'Capacitor'

  # MetaMapSDK dependency, which brings in Incode SDK
  s.dependency 'MetaMapSDK', '3.22.5'

  # Use static framework to support dependencies like Incode SDK
  s.static_framework = true

  # Swift version
  s.swift_version = '5.1'
end